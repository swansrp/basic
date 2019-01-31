/**
 * @Title: CustomAuthenticationFailureHandler.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author Sharp
 * @date 2019-01-31 15:51:55
 */
package com.srct.service.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author Sharp
 *
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {}

    /**
     * 通过检查异常类型实现页面跳转控制
     */
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException) {
            httpServletResponse.sendRedirect("/login/page?inexistent");
        } else if (e instanceof DisabledException) {
            httpServletResponse.sendRedirect("/login/page?disabled");
        } else if (e instanceof AccountExpiredException) {
            httpServletResponse.sendRedirect("/login/page?expired");
        } else if (e instanceof LockedException) {
            httpServletResponse.sendRedirect("/login/page?locked");
        } else {
            httpServletResponse.sendRedirect("/login/page?error");
        }
    }

}
