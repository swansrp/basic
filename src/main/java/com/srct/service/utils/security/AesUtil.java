package com.srct.service.utils.security;

import com.srct.service.utils.CommonEnum;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Title: AesUtil
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/9 9:45
 * @description Project Name: Grote
 * @Package: com.srct.service.utils.security
 */
public class AesUtil {
    public static final String PKCS5PADDING_MODE = "AES/ECB/PKCS7Padding";
    public static final String PKCS7PADDING_MODE = "AES/ECB/PKCS7Padding";

    public static String encrypt(String content, String password, String mode) {
        return encrypt(content, password, mode, CommonEnum.SecurityEnum.CHARSET_UTF8);
    }

    public static String encrypt(String content, String password, String mode, String charset) {
        try {
            SecretKeySpec key = getSecretKeySpec(password);
            Security.addProvider(new BouncyCastleProvider());
            // 创建密码器
            Cipher cipher = Cipher.getInstance(mode);
            byte[] byteContent = content.getBytes(charset);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            if (result != null && result.length > 0) {
                /*
                 * 注意： return new String(result,ENCRYPT_CHARSET);
                 * 会出现javax.crypto.IllegalBlockSizeException: Input length must be multiple of
                 * 16 when decrypting with padded cipher 加密后的byte数组是不能强制转换成字符串的，
                 * 字符串和byte数组在这种情况下不是互逆的； 需要将二进制数据转换成十六进制表示
                 */
                return parseByte2HexStr(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        secureRandom.setSeed(password.getBytes());
        keyGenerator.init(128, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String decrypt(String content, String password, String mode) {
        return decrypt(content, password, mode, CommonEnum.SecurityEnum.CHARSET_UTF8);
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password, String mode, String charset) {
        try {
            if (content == null) {
                return null;
            }
            // 将16进制转换为二进制
            byte[] newContent = parseHexStr2Byte(content);
            SecretKeySpec key = getSecretKeySpec(password);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(mode);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(newContent);
            if (result != null && result.length > 0) {
                return new String(result, Charset.forName(charset));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
