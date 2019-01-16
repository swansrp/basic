/**  
 * Project Name:SpringBootCommon  
 * File Name:Base64Util.java  
 * Package Name:com.srct.service.utils.security  
 * Date:Apr 28, 2018 5:09:10 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.utils.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * ClassName:Base64Util <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 28, 2018 5:09:10 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class Base64Util {

    private Base64Util() {
    }

    static final Base64.Decoder urlDecoder = Base64.getUrlDecoder();

    static final Base64.Encoder urlEncoder = Base64.getUrlEncoder();

    static final Base64.Decoder decoder = Base64.getDecoder();

    static final Base64.Encoder encoder = Base64.getEncoder();

    public static String encode(String src, boolean urlSafe) throws UnsupportedEncodingException {
        if (urlSafe) {
            return urlEncoder.encodeToString(src.getBytes("UTF-8"));
        } else {
            return encoder.encodeToString(src.getBytes("UTF-8"));
        }
    }

    public static String encode(String src) throws UnsupportedEncodingException {
        return encode(src, true);
    }

    public static String encode(byte[] src, boolean urlSafe) throws UnsupportedEncodingException {
        if (urlSafe) {
            return urlEncoder.encodeToString(src);
        } else {
            return encoder.encodeToString(src);
        }
    }

    public static String encode(byte[] src) throws UnsupportedEncodingException {
        return encode(src, true);
    }

    public static String decode(String src, boolean urlSafe) throws UnsupportedEncodingException {
        if (urlSafe) {
            return new String(urlDecoder.decode(src), "UTF-8");
        } else {
            return new String(decoder.decode(src), "UTF-8");
        }
    }

    public static String decode(String src) throws UnsupportedEncodingException {
        return decode(src, true);
    }

    public static String decode(byte[] src, boolean urlSafe) throws UnsupportedEncodingException {
        if (urlSafe) {
            return new String(urlDecoder.decode(src), "UTF-8");
        } else {
            return new String(decoder.decode(src), "UTF-8");
        }
    }

    public static String decode(byte[] src) throws UnsupportedEncodingException {
        return decode(src, true);
    }
}
