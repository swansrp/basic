/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author: ruopeng.sha
 * @date: 2018-07-20 15:01
 */
package com.srct.service.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class AuthorizationConfigureAdapter implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationConfigureAdapter.class);

    @Resource
    private AuthBaseInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }
}
