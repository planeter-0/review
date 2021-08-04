package com.planeter.review.config;


import com.planeter.review.security.AuthFilter;
import com.planeter.review.security.LoginFilter;
import com.planeter.review.security.MyDeniedHandler;
import com.planeter.review.security.MyEntryPoint;
import com.planeter.review.serviceimpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * @description: Spring Security配置类
 * @author Planeter
 * @date 2021/7/19 18:06
 * @status dev
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserServiceImpl userDetailsService;
    @Resource
    private LoginFilter loginFilter;
    @Resource
    private AuthFilter authFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf和frameOptions
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 开启跨域
        http.cors();
        // 配置接口防护
        http.authorizeRequests()
                //跨域连调
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/**").authenticated()
                .and().exceptionHandling().authenticationEntryPoint(new MyEntryPoint()).accessDeniedHandler(new MyDeniedHandler());

        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 注册自定义的认证授权过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定UserDetailService和BCrypt加密器
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}