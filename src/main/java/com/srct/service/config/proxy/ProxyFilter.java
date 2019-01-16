/**  
 * Project Name:SpringBootCommon  
 * File Name:ProxyFilter.java  
 * Package Name:com.srct.service.config.proxy  
 * Date:May 2, 2018 2:47:20 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.config.proxy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

import com.srct.service.utils.log.Log;

/**
 * ClassName:ProxyFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: May 2, 2018 2:47:20 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
@Configuration
@WebFilter(filterName = "proxyFilter", urlPatterns = "/*")
public class ProxyFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        Log.i("...ProxyFilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();
        String queryString = req.getQueryString();
        Log.d("...ProxyFilter doFilter...");
        Log.d("req:" + requestURI);
        Log.d("query:" + queryString);
        if (requestURI != null && requestURI.contains("elb.check")) {
            return;
        }
        if (queryString != null && queryString.contains("redirect=")) {
            RequestDispatcher rd = req.getRequestDispatcher("/redirect");
            req.setAttribute("requestURI", requestURI);
            rd.forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Log.d("...ProxyFilter destroy...");
    }
}
