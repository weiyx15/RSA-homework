package utils;

import java.io.UnsupportedEncodingException;

/**
 * Deal with string encoding and decoding
 * @Author weiyuxuan, weiyuxua19@mails.tsinghua.edu.cn
 * @Time 2019-10-26
 */
public class StringUtils {
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(Integer.toString(b & 0xff, 16));
        }
        return sb.toString();
    }

    public static String byteArrayToUtf8String(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }

    public static String utf8ToHex(String utf8) {
        byte[] bytes;
        try {
            bytes = utf8.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            bytes = utf8.getBytes();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(Integer.toString(b & 0xff, 16));
        }
        return sb.toString();
    }

    public static String hexToUtf8(String hex) {
        int n=hex.length();
        byte[] bytes = new byte[n/2];
        for (int i=0; i<n; i += 2) {
            bytes[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
        }
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }

    public static boolean checkHexString(String s) {
        for (char ch: s.toCharArray()) {
            if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f'))) {
                return false;
            }
        }
        return true;
    }
}
