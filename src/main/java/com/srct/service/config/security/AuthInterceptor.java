/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author: ruopeng.sha
 * @date: 2018-07-20 15:03
 */
package com.srct.service.config.security;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.config.holder.TokenHolder;
import com.srct.service.service.AuthTokenService;
import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTokenOperateService redisTokenOperateService;

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class clazz = handlerMethod.getBeanType();
        Auth.AuthType authType = Auth.AuthType.NONE;

        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = method.getAnnotation(Auth.class);
            authType = auth.role();
        } else if (clazz.isAnnotationPresent(Auth.class)) {
            Auth auth = (Auth) clazz.getDeclaredAnnotation(Auth.class);
            authType = auth.role();
        }

        authTokenService.validate(request, response, authType);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        TokenHolder.remove();
        ClientTypeHolder.remove();
        super.postHandle(request, response, handler, modelAndView);
    }
        /*
        String holder = request.getHeader("accessToken");
        if (holder.equals("test")) {
            return true;
        }
        if (StringUtils.isEmpty(holder)) {
            throw new UserNotLoginException();
        }
        String uid = redisTokenOperateService.getUid(holder);
        if (StringUtils.isEmpty(uid)) {
            throw new AccessTokenExpiredException();
        }
        String serverToken = redisTokenOperateService.getAccessToken(Integer.valueOf(uid));
        if (StringUtils.isEmpty(serverToken)) {
            throw new AccessTokenExpiredException();
        }
        if (!holder.equals(serverToken)) {
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
                    Log.i(this.getClass(), "roles = " + methodRole);
                    if (methodRole == role)
                        hasAuth = true;
                });
            });
        if (hasAuth) {
            hasAuth = false;
            return true;
        }
        throw new ServiceException();

    }*/

}
