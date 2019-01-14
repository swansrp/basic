package com.srct.service.config.db;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

    /**
     * Regist StatViewServlet
     * 
     * @return
     */
    @Bean
    public ServletRegistrationBean<Servlet> DruidStatViewServle() {
        // org.springframework.boot.context.embedded.ServletRegistrationBea
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(),
                "/druid/*");
        // whiteList：
        // servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        // IP blackList (deny has high priority than allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        servletRegistrationBean.addInitParameter("loginUsername", "admin2");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * Regist filterRegistrationBean
     * 
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> druidStatFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
