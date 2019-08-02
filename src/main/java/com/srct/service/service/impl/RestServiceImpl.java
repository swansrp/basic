/**
 * @Title: RestServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service.impl
 * @author sharp
 * @date 2019-01-23 09:38:00
 */
package com.srct.service.service.impl;

import com.srct.service.service.RestService;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author sharp
 */
@Slf4j
@Service
public class RestServiceImpl implements RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T get(String url, Class<T> clazz) {
        return exec(url, HttpMethod.GET, new HttpHeaders(), clazz, null, null);
    }

    @Override
    public <T> T get(String url, HttpHeaders header, Class<T> clazz) {
        return exec(url, HttpMethod.GET, header, clazz, null, null);
    }

    @Override
    public <T> T get(String url, Object param, Class<T> clazz) {
        return exec(url, HttpMethod.GET, new HttpHeaders(), clazz, param, null);
    }

    @Override
    public <T> T get(String url, HttpHeaders header, Object param, Class<T> clazz) {
        return exec(url, HttpMethod.GET, header, clazz, param, null);
    }

    @Override
    public <T> T post(String url, Class<T> clazz, Object body) {
        return exec(url, HttpMethod.POST, new HttpHeaders(), clazz, null, body);
    }

    @Override
    public <T> T post(String url, Class<T> clazz, LinkedMultiValueMap body) {
        return exec(url, HttpMethod.POST, new HttpHeaders(), clazz, null, body);
    }

    @Override
    public <T> T post(String url, HttpHeaders header, Class<T> clazz, Object body) {
        return exec(url, HttpMethod.POST, header, clazz, null, body);
    }

    @Override
    public <T> T post(String url, HttpHeaders header, Class<T> clazz, LinkedMultiValueMap body) {
        return exec(url, HttpMethod.POST, header, clazz, null, body);
    }

    @Override
    public <T> T post(String url, Class<T> clazz, Object param, Object body) {
        return exec(url, HttpMethod.POST, new HttpHeaders(), clazz, param, body);
    }

    @Override
    public <T> T post(String url, Class<T> clazz, Object param, LinkedMultiValueMap body) {
        return exec(url, HttpMethod.POST, new HttpHeaders(), clazz, param, body);
    }

    @Override
    public <T> T post(String url, HttpHeaders header, Class<T> clazz, Object param, Object body) {
        return exec(url, HttpMethod.POST, header, clazz, param, body);
    }

    @Override
    public <T> T post(String url, HttpHeaders header, Class<T> clazz, Object param, LinkedMultiValueMap body) {
        return exec(url, HttpMethod.POST, header, clazz, param, body);
    }

    @Override
    public <T> T exec(String url, HttpMethod method, HttpHeaders header, Class<T> clazz, Object param, Object body) {
        ResponseEntity<T> response;
        ParameterizedTypeReference<T> parameter = new ParameterizedTypeReference<T>() {
        };
        if (param != null) {
            url = buildUrl(url, param);
            log.info("url : {}", url);
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, header);
        try {
            log.info("==> {}", JSONUtil.toJSONString(body));
            response = restTemplate.exchange(url, method, entity, parameter);
            log.info("<== {}", JSONUtil.toJSONString(response.getBody()));
            try {
                return JSONUtil.readJson(response.getBody(), clazz);
            } catch (Exception e) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private String buildUrl(String url, Object param) {
        String paramUrl = "?";
        Map<String, Object> paramMap = ReflectionUtil.getHashMap(param);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            try {
                paramUrl =
                        paramUrl + entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8") + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url + paramUrl.substring(0, paramUrl.lastIndexOf('&'));
    }
}
