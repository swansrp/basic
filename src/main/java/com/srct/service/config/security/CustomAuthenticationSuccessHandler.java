/**
 * @Title: CustomAuthenticationSuccessHandler.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author Sharp
 * @date 2019-01-31 15:50:59
 */
package com.srct.service.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author Sharp
 *
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public CustomAuthenticationSuccessHandler() {}

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect("/index");
        // 可以自定义登录成功后的其它动作，如记录用户登录日志、发送上线消息等
    }

}
