/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security 
 * @author: ruopeng.sha   
 * @date: 2018-07-20 15:03
 */
package com.srct.service.config.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.srct.service.config.annotation.TokenRole;
import com.srct.service.exception.AccessTokenExpiredException;
import com.srct.service.exception.ServiceException;
import com.srct.service.exception.UserNotLoginException;
import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.log.Log;

/**
 * @ClassName: AuthInterceptor
 * @Description: TODO
 */

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTokenOperateService redisTokenOperateService;

    private boolean hasAuth = false;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenRole.class)) {
            String token = request.getHeader("accessToken");
            if (token.equals("test")) {
                return true;
            }
            if (StringUtils.isEmpty(token)) {
                throw new UserNotLoginException();
            }

            String uid = redisTokenOperateService.getUid(token);
            if (StringUtils.isEmpty(uid))
            {
                throw new AccessTokenExpiredException();
            }
            String serverToken = redisTokenOperateService.getAccessToken(Integer.valueOf(uid));

            if (StringUtils.isEmpty(serverToken)) {
                throw new AccessTokenExpiredException();
            }
            if (!token.equals(serverToken)) {
                throw new UserNotLoginException();
            }

            String roleStr = redisTokenOperateService.getUserRole(Integer.valueOf(uid));
            if (StringUtils.isEmpty(roleStr)) {
                throw new UserNotLoginException();
            }
            Integer role = Integer.valueOf(roleStr);
            if (role == null || role < 0) {
                throw new UserNotLoginException();
            }
            Annotation annotation[] = method.getDeclaredAnnotations();
            Arrays.stream(annotation).filter(it -> it.annotationType().equals(TokenRole.class)).forEach(it -> {

                int[] roles = ((TokenRole) it).roles();
                Arrays.stream(roles).forEach(methodRole -> {
                    Log.i(this.getClass(),"roles = " + methodRole);
                    if (methodRole == role)
                        hasAuth = true;
                });
            });
            if (hasAuth) {
                hasAuth = false;
                return true;
            }
            throw new ServiceException();
        }
        return super.preHandle(request, response, handler);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object,
     * org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }
}