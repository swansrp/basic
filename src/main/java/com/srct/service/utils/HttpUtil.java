package com.srct.service.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.StrUtil;

public class HttpUtil {

    private final static String COMMA = ",";

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

    public static Map<String, String> getHeadersInfoMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isUnknown(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isUnknown(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isUnknown(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StrUtil.isNotBlank(ip) && ip.contains(StrUtil.COMMA)) {
            String[] list = StrUtil.split(ip, StrUtil.COMMA);
            if (list != null && list.length > 1) {
                ip = list[0];
            }
        }

        return cutLength(ip);
    }

    private static boolean isUnknown(String ip) {
        return StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }

    private static String cutLength(String ip) {
        String tempIp = ip;

        final int size = 20;
        if (StrUtil.isNotBlank(ip) && ip.length() > size) {
            tempIp = ip.substring(0, size);
        }

        return tempIp;
    }
}
