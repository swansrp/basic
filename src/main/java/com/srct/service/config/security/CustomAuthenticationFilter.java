/**
 * @Title: CustomAuthenticationFilter.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author Sharp
 * @date 2019-01-31 15:47:18
 */
package com.srct.service.config.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Sharp
 *
 */
@Configuration
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
        AuthenticationFailureHandler authenticationFailureHandler,
        AuthenticationSuccessHandler authenticationSuccessHandler) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse resonse)
        throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        /*
        // 添加验证码校验功能
        String captcha = request.getParameter("captcha");
        if (!checkCaptcha(captcha)) {
            throw new AuthenticationException("Invalid captcha!");
        }
        */
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        username = Objects.isNull(username) ? "" : username.trim();
        password = Objects.isNull(password) ? "" : password;

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);

    }
}
