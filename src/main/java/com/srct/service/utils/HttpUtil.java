package com.srct.service.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    /**
     * Extract request header
     * 
     * @param req
     *            http request
     * @return
     */
    public static String getHeader(HttpServletRequest req, String name) {
        return req.getHeader(name);
    }
}
