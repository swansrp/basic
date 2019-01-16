/**  
 * Project Name:SpringBootCommon  
 * File Name:AppInfoUtil.java  
 * Package Name:com.srct.service.utils.security  
 * Date:Apr 28, 2018 3:47:03 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.utils.security;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.srct.service.utils.log.Log;

/**
 * ClassName:AppInfoUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 28, 2018 3:47:03 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class AppInfoUtil {

    private AppInfoUtil() {
    }

    public static class AppInfo {

        private String AppId;

        private String AppSecret;

        private String PartnerId;

        /**
         * appId.
         * 
         * @return the appId
         * @since JDK 1.8
         */
        public String getAppId() {
            return AppId;
        }

        /**
         * appId.
         * 
         * @param appId
         *            the appId to set
         * @since JDK 1.8
         */
        public void setAppId(String appId) {
            AppId = appId;
        }

        /**
         * appSecret.
         * 
         * @return the appSecret
         * @since JDK 1.8
         */
        public String getAppSecret() {
            return AppSecret;
        }

        /**
         * appSecret.
         * 
         * @param appSecret
         *            the appSecret to set
         * @since JDK 1.8
         */
        public void setAppSecret(String appSecret) {
            AppSecret = appSecret;
        }

        /**
         * partnerId.
         * 
         * @return the partnerId
         * @since JDK 1.8
         */
        public String getPartnerId() {
            return PartnerId;
        }

        /**
         * partnerId.
         * 
         * @param partnerId
         *            the partnerId to set
         * @since JDK 1.8
         */
        public void setPartnerId(String partnerId) {
            PartnerId = partnerId;
        }
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

    public static String getId() {
        return MD5Util.MD5(getUUID());
    }

    public static AppInfo getAppInfo(String appName) {
        AppInfo app = new AppInfo();
        String uuid = getUUID();
        app.setAppId(uuid);
        String rawAppSKey = MD5Util.MD5(appName + ":" + RandomUtil.getString(appName.length()));
        Log.d("AppSecret raw data = " + rawAppSKey);
        app.setAppSecret(MD5Util.generate(rawAppSKey));
        app.setPartnerId(MD5Util.MD5(appName));
        return app;
    }
}
