/**
 * @Title: RestServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service.impl
 * @author sharp
 * @date 2019-01-23 09:38:00
 */
package com.srct.service.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.srct.service.service.RestService;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.log.MySlf4j;

/**
 * @author sharp
 *
 */
@Service
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class RestServiceImpl implements RestService {

    @Autowired
    private RestTemplate restTemplate;

    private RestServiceImpl self;

    @PostConstruct
    public void init() {
        self = AopContext.currentProxy() != null ? (RestServiceImpl)AopContext.currentProxy() : this;
    }

    @Override
    public <T> T get(String url, HttpHeaders header, Class<T> clazz) {
        return self.exec(url, HttpMethod.GET, header, clazz, null);
    }

    @Override
    public <T> T post(String url, HttpHeaders header, Class<T> clazz, Object req) {
        return self.exec(url, HttpMethod.POST, header, clazz, req);
    }

    @Override
    @MySlf4j
    public <T> T exec(String url, HttpMethod method, HttpHeaders header, Class<T> clazz, Object req) {
        ResponseEntity<T> response = null;
        ParameterizedTypeReference<T> parameter = new ParameterizedTypeReference<T>() {};
        HttpEntity<String> entity = new HttpEntity<>(JSONUtil.toJSONString(req), header);
        try {
            response = restTemplate.exchange(url, method, entity, parameter);
            return response.getBody();
        } catch (Exception e) {
            Log.e(e);
            return null;
        }
    }
}
