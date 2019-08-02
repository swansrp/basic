/**
 * Project Name:SpringBootCommon
 * File Name:RandomString.java
 * Package Name:com.srct.service.utils.security
 * Date:Apr 28, 2018 3:57:56 PM
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 *
*/
package com.srct.service.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import com.srct.service.utils.log.Log;

/**
 * ClassName:RandomString <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 28, 2018 3:57:56 PM <br/>
 *
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class RandomUtil {

    private RandomUtil() {
    }

    private static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * Generate a random string.
     *
     * @param length the length of the generated string.
     * @return string
     */
    public static String getString(int length) {
        Random random = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
    }

    public static String getNumber() {
        String suffix = getUUIDNumber(5);
        String sdf = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        return sdf + suffix;
    }

    public static Integer getNumber(Integer start, Integer end) {
        Random random = new SecureRandom();
        return random.nextInt(end - start) + start;
    }

    public static String getUUIDNumber(int length) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        int value = Math.abs(hashCodeV);
        if (length > 10) {
            String format = "%" + length + "d";
            return String.format(format, value);
        } else {
            return String.valueOf(value).substring(0, length);
        }
    }

    public static String getUUID() {
        String uuid22 = null;
        try {
            uuid22 = Base64Util.encode(compress(UUID.randomUUID()));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (uuid22 == null) {
            Log.d("uuid22 generate fail");
            return null;
        }
        String res = uuid22.substring(0, uuid22.length() - 2);
        return res;
    }

    private static byte[] compress(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) ((msb >>> 8 * (7 - i)) & 0xFF);
            buffer[i + 8] = (byte) ((lsb >>> 8 * (7 - i)) & 0xFF);
        }
        return buffer;
    }
}
