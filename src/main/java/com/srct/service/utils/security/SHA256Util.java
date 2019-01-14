package com.srct.service.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {
    /**
     * common SHA256
     * 
     * @author Date: July 3, 2018 3:53:54 PM
     * @author ruopeng.sha
     * @return
     */
    public static String SHA256(String input) {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] sha256Bytes = sha256.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < sha256Bytes.length; i++) {
            int val = ((int) sha256Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
}
