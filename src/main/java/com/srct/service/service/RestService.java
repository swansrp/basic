/**
 * @Title: RestService.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service
 * @author sharp
 * @date 2019-01-23 09:37:10
 */
package com.srct.service.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * @author sharp
 *
 */
public interface RestService {

    /**
     * @param url
     * @param clazz
     * @return
     */
    <T> T get(String url, Class<T> clazz);

    /**
     * @param url
     * @param header
     * @param clazz
     * @return
     */
    <T> T get(String url, HttpHeaders header, Class<T> clazz);

    /**
     * @param url
     * @param clazz
     * @param req
     * @return
     */

    <T> T post(String url, Class<T> clazz, Object req);

    /**
     * @param url
     * @param header
     * @param clazz
     * @param req
     * @return
     */
    <T> T post(String url, HttpHeaders header, Class<T> clazz, Object req);

    /**
     * @param url
     * @param method
     * @param header
     * @param clazz
     * @param req
     * @return
     */
    <T> T exec(String url, HttpMethod method, HttpHeaders header, Class<T> clazz, Object req);

}
