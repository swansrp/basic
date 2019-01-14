/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.utils.security 
 * @author: xu1223.zhang   
 * @date: 2018-09-17 08:59
 */
package com.srct.service.utils.security;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.srct.service.exception.ServiceException;
import com.srct.service.utils.CommonEnum;
import com.srct.service.utils.CommonUtil;
import com.srct.service.utils.StreamUtil;
import com.srct.service.utils.codec.Base64;

/**
 * @ClassName: SignatureUtil
 * @Description: TODO
 */
public class SignatureUtil {
    private SignatureUtil() {

    }

    /**
     * rsa sign
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws AlipayApiException
     */
    public static String rsaSign(String content, String privateKey, String charset, String signType)
            throws ServiceException {

        if (CommonEnum.SecurityEnum.SIGN_TYPE_RSA.equals(signType)) {

            return rsaSign(content, privateKey, charset);
        } else if (CommonEnum.SecurityEnum.SIGN_TYPE_RSA2.equals(signType)) {

            return rsa256Sign(content, privateKey, charset);
        } else {

            throw new ServiceException("Sign Type is Not Support : signType=" + signType);
        }

    }

    /**
     * sha256WithRsa sign
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws ServiceException
     */
    public static String rsa256Sign(String content, String privateKey, String charset) throws ServiceException {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(CommonEnum.SecurityEnum.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (CommonUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new ServiceException("RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    /**
     * sha1WithRsa sign
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws ServiceException
     */
    public static String rsaSign(String content, String privateKey, String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(CommonEnum.SecurityEnum.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (CommonUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new ServiceException(
                    "RSA private key format invalid，please check if provide private key with PKCS8 format ", ie);
        } catch (Exception e) {
            throw new ServiceException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    ///////////////////////// Check Sign///////////////////////////
    /*
     * only support RSA2/RSA & UTF-8/GBK sign check
     */
    public static boolean checkContentSign(String content, String sign, String publicKey, String charset,
            String signType) {

        if ("RSA2".equals(signType)) {
            return rsa256checkContent(content, sign, publicKey, charset);
        } else if ("RSA".equals(signType)) {
            return rsacheckContent(content, sign, publicKey, charset);
        } else {
            throw new ServiceException("content = " + content + " sign = " + sign);
        }
    }

    private static boolean rsa256checkContent(String content, String sign, String publicKey, String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(CommonEnum.SecurityEnum.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (CommonUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new ServiceException("content = " + content + " sign = " + sign);
        }
    }

    private static boolean rsacheckContent(String content, String sign, String publicKey, String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509(CommonEnum.SecurityEnum.RSA_ALG,
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(CommonEnum.SecurityEnum.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (CommonUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new ServiceException("content = " + content + " sign = " + sign);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || CommonUtil.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = StreamUtil.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * 
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String[]> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key) != null ? sortedParams.get(key)[0] : null;
            if (CommonUtil.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }
}
