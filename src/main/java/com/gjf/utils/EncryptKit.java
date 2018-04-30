package com.gjf.utils;

import org.springframework.lang.NonNull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: GJF
 * @Date : 2018/04/28
 * Time   : 17:20
 */
public class EncryptKit {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16进制加盐密文
     */
    public static String md5(@NonNull String data, @NonNull String salt) {
        if (data.length() < 1 || salt.length() < 1) {
            return null;
        }
        return bytes2HexString(md5ToByte((data + salt).getBytes()));
    }

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    private static String bytes2HexString(@NonNull byte[] bytes) {
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret).toLowerCase();
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    private static byte[] md5ToByte(@NonNull byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    static byte[] algToByte(@NonNull byte[] data,String algorithm) {
        return hashTemplate(data,algorithm);
    }

    /**
     * hash加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(@NonNull byte[] data, @NonNull String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

