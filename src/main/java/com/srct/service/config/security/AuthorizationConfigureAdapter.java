/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.security
 * @author: ruopeng.sha
 * @date: 2018-07-20 15:01
 */
package com.srct.service.config.security;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName: AuthorizationConfigureAdapter
 * @Description: TODO
 */
@Configuration
public class AuthorizationConfigureAdapter extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationConfigureAdapter.class);
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.config.annotation.
     * WebMvcConfigurationSupport#
     * addInterceptors(org.springframework.web.servlet.config.annotation.
     * InterceptorRegistry)
     */
    @Autowired
    AuthInterceptor authInterceptor;
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.config.annotation.
     * WebMvcConfigurationSupport#
     * addResourceHandlers(org.springframework.web.servlet.config.annotation.
     * ResourceHandlerRegistry)
     */

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        // registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor).addPathPatterns("/rfu/**");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    /*for shiro servletContext loss issue*/
    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
    }
}
