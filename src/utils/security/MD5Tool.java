package utils.security;

import java.security.MessageDigest;

public class MD5Tool {
    /**
       createTime: 2017/2/28
       Description: MD5加密
       @param inStr：加密字符串
       @return String：加密后的字符串
    */
    public static String md5Encode(String inStr)throws Exception{
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
