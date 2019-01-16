/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.utils.security 
 * @author: xu1223.zhang   
 * @date: 2018-09-27 10:31
 */
package com.srct.service.utils.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.srct.service.exception.ServiceException;
import com.srct.service.utils.CommonEnum;
import com.srct.service.utils.CommonUtil;
import com.srct.service.utils.StreamUtil;
import com.srct.service.utils.codec.Base64;
import com.srct.service.utils.log.Log;

/**
 * @ClassName: EncryptUtil
 * @Description: TODO
 */
public class EncryptUtil {

    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";

    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private EncryptUtil() {
    }

    /**
     * 
     * @Title: encryptContent
     * @Description: TODO
     * @param content
     * @param encryptType
     * @param encryptKey
     * @param charset
     * @return
     * @throws ServiceException
     *             String
     */
    public static String encryptContent(String content, String encryptType, String encryptKey, String charset) {
        if (CommonEnum.SecurityEnum.AES_ALG.equals(encryptType)) {
            return aesEncrypt(content, encryptKey, charset);
        } else if (CommonEnum.SecurityEnum.RSA_ALG.equals(encryptType)) {
            return rsaEncrypt(content, encryptKey, charset);
        } else {
            throw new ServiceException("Not Support encryptType ：encrypeType=" + encryptType);
        }
    }

    /**
     * 
     * @Title: decryptContent
     * @Description: TODO
     * @param content
     * @param encryptType
     * @param encryptKey
     * @param charset
     * @return
     * @throws ServiceException
     *             String
     */
    public static String decryptContent(String content, String encryptType, String encryptKey, String charset)
            throws ServiceException {
        if (CommonEnum.SecurityEnum.AES_ALG.equals(encryptType)) {
            return aesDecrypt(content, encryptKey, charset);
        } else if (CommonEnum.SecurityEnum.RSA_ALG.equals(encryptType)) {
            return rsaDecrypt(content, encryptKey, charset);
        } else {
            throw new ServiceException("Not Support encryptType ：encrypeType=" + encryptType);
        }
    }

    /**
     * 
     * @Title: aesEncrypt
     * @Description: TODO
     * @param content
     * @param aesKey
     * @param charset
     * @return
     * @throws ServiceException
     *             String
     */
    private static String aesEncrypt(String content, String aesKey, String charset) throws ServiceException {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), CommonEnum.SecurityEnum.AES_ALG), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encodeBase64(encryptBytes));
        } catch (Exception e) {
            throw new ServiceException("AES entrypt failed：Aescontent = " + content + "; charset = " + charset, e);
        }
    }

    /**
     * 
     * @Title: aesDecrypt
     * @Description: TODO
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws ServiceException
     *             String
     */
    private static String aesDecrypt(String content, String key, String charset) throws ServiceException {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(initIv(AES_CBC_PCK_ALG));
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(Base64.decodeBase64(key.getBytes()), CommonEnum.SecurityEnum.AES_ALG), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            throw new ServiceException("AES decrypt failed：Aescontent = " + content + "; charset = " + charset, e);
        }
    }

    /**
     * 
     * @Title: rsaEncrypt
     * @Description: TODO
     * @param content
     * @param publicKey
     * @param charset
     * @return
     * @throws ServiceException
     *             String+
     * 
     */
    private static String rsaEncrypt(String content, String publicKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(CommonEnum.SecurityEnum.RSA_ALG);
            PublicKey pubKey = getPublicKeyFromX509(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(publicKey.getBytes()));
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = content.getBytes();
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // Note : Must use Base64 to encode encryted data, Dont use new
            // String() method
            return java.util.Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("RSA encrypt failed：content = " + content + "; charset = " + charset, e);
        }
    }

    /**
     * 
     * @Title: rsaDecrypt
     * @Description: TODO
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws ServiceException
     *             String
     */
    private static String rsaDecrypt(String content, String privateKey, String charset) throws ServiceException {
        try {
            Cipher cipher = Cipher.getInstance(CommonEnum.SecurityEnum.RSA_ALG);
            PrivateKey priKey = getPrivateKeyFromPKCS8(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(privateKey.getBytes()));
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] data = java.util.Base64.getDecoder().decode(content);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            throw new ServiceException("RSA decrypt failed：content = " + content + "; charset = " + charset, e);
        }
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || CommonUtil.isEmpty(algorithm)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * 初始向量的方法, 全部为0. 这里的写法适合于其它算法,针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] initIv(String fullAlg) {
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        } catch (Exception e) {
            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);// 要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        Log.i(" b = " + new String(b));
        return byteToHexString(b);
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
