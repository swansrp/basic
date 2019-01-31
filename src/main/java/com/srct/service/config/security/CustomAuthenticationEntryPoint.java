/**
 * @Title: CustomAuthenticationEntryPoint.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author Sharp
 * @date 2019-01-31 16:40:16
 */
package com.srct.service.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Sharp
 *
 */
@Component
public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl
     */
    public CustomAuthenticationEntryPoint() {
        super("/login");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void
        commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // super.commence(request, response, authException);

        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.getWriter().println("{\"ok\":0,\"msg\":\"" + authException.getLocalizedMessage() + "\"}");
        response.getWriter().flush();
    }

}
