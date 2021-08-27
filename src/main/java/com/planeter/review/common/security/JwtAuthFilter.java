package com.planeter.review.common.security;


import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.service.UserService;
import com.planeter.review.utils.JwtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/1 14:23
 * @status dev
 */
@Slf4j
public class JwtAuthFilter extends AuthenticatingFilter {
    // token更新时间.单位秒
    private static final int tokenRefreshInterval = 3000;


    /**
     * 父第一个被调用的方法
     * 返回true则继续，返回false则会调用onAccessDenied()。
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response))
            return true; // 放行登录请求
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response); //处理登录:createToken()
        } catch (IllegalStateException e) {
            log.warn("Invalid token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //从header获取token value
        HttpServletRequest httpRequest = WebUtils.toHttp(servletRequest);
        String header = httpRequest.getHeader("Authorization");
        String jwtToken = StringUtils.removeStart(header, "Bearer ");
        // 非空且不过期,返回原token
        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)) {
            return new JwtToken(jwtToken);
        }
        return null;// isAccessAllowed() return false
    }

    /**
     * isAccessAllowed()返回false,返回错误响应
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
        httpResponse.sendError(203,"认证错误");
        return false;
    }

    /**
     * 认证成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JwtToken) {
            JwtToken jwtToken = (JwtToken) token;
            UserEntity user = (UserEntity) subject.getPrincipal();
            //检查token过期
            String tokenValue = StringUtils.removeStart(jwtToken.getToken(), "Bearer ");
            Date date = JwtUtils.getIssuedAt(tokenValue);
            assert date != null;
            // 更新token
            if (shouldTokenRefresh(date)) {
                newToken = JwtUtils.sign(user.getUsername(),3000);
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                RedisTemplate<String,Object> redisTemplate = (RedisTemplate<String,Object>) factory.getBean("redisTemplate");
                redisTemplate.opsForValue().set("Jwt-" + user.getUsername(), newToken, 9000, TimeUnit.SECONDS);
            }
        }
        if (StringUtils.isNotBlank(newToken))
            httpResponse.setHeader("Set-Token", newToken);
        response = WebUtils.getResponse(httpResponse);
        return true;
    }

    /**
     * 认证失败，回调
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    /**
     * 比较 jwt生成时间 是否 晚于 (现在时间 - jwt有效时间 )
     */
    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }
}
