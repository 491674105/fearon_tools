package utils.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密工具
 *
 * @author fearon
 * @date 2019/5/17 10:01:00
 */
public class MD5Tool {
    /**
     * @param inStr：加密字符串
     * @return String：加密后的字符串
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
