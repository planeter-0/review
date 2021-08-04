package com.planeter.review.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 授权过滤器
 */
@Slf4j
@Component
public class AuthFilter extends AbstractSecurityInterceptor implements Filter {
    @Resource
    private SecurityMetadataSource securityMetadataSource;

    // 获取自定义的SecurityMetadataSource
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        // 将自定义的AccessDecisionManager给注入
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("---AuthFilter---");

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // 调用父类的AbstractSecurityInterceptor的方法,也就是调用了accessDecisionManager
        InterceptorStatusToken token = super.beforeInvocation(fi);

        try {
            // 执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }  finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}

