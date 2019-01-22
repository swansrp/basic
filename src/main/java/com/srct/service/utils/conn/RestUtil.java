/**
 * <p>
 * Title: RestUtil.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company: Sharp
 * </p>
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.utils.conn
 * @author srct
 * @date 2019-01-22 17:43:45
 */
package com.srct.service.utils.conn;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import cn.hutool.json.JSONUtil;

/**
 * @author Sharp
 *
 */
public class RestUtil {

    public static <T> T get(String url, HttpHeaders header, Class<T> clazz) {
        return exec(url, HttpMethod.GET, header, clazz, null);
    }

    public static <T> T post(String url, HttpHeaders header, Class<T> clazz, Object req) {
        return exec(url, HttpMethod.POST, header, clazz, req);
    }

    private static <T> T exec(String url, HttpMethod method, HttpHeaders header, Class<T> clazz, Object req) {
        RestTemplate restTemplate = (RestTemplate)BeanUtil.getBean("restTemplate");
        ResponseEntity<T> response = null;
        ParameterizedTypeReference<T> parameter = new ParameterizedTypeReference<T>() {};
        HttpEntity<String> entity = new HttpEntity<>(JSONUtil.toJsonStr(req), header);
        try {
            response = restTemplate.exchange(url, method, entity, parameter);
            return response.getBody();
        } catch (Exception e) {
            Log.e(e);
            return null;
        }
    }

}
