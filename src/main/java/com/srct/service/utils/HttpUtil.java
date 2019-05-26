package com.srct.service.utils;

import com.srct.service.utils.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtil {

    private final static String COMMA = ",";

    /**
     * Extract request header
     *
     * @param req http request
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

        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            String[] list = StringUtils.split(ip, ",");
            if (list != null && list.length > 1) {
                ip = list[0];
            }
        }

        return cutLength(ip);
    }

    private static boolean isUnknown(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }

    private static String cutLength(String ip) {
        String tempIp = ip;

        final int size = 20;
        if (StringUtils.isNotBlank(ip) && ip.length() > size) {
            tempIp = ip.substring(0, size);
        }

        return tempIp;
    }

    /**
     *     * @Title: contentDisposition
     * <p>
     *     * @Description:解决不同浏览器上文件下载的中文名乱码问题
     * <p>
     *     * @paramfilename导出/下载的文件的文件名
     * <p>
     *     * @param request
     * <p>
     *     * @param response
     * <p>
     *     
     */

    public static void contentDisposition(String filename, HttpServletRequest request, HttpServletResponse response) {
        try {
            // response.reset();
            final String userAgent = request.getHeader("USER-AGENT");
            log.info(userAgent);
            if (StringUtils.contains(userAgent, "Trident") || StringUtils.contains(userAgent, "Edge")) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla") || StringUtils.contains(userAgent, "Chrome")) {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");
            }
            Log.i(filename);
            response.setHeader("content-disposition", "attachment;filename=\"" + filename + "\"");
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException.");
        }
    }
}
