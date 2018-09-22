package utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.random.CheckCodeCreater;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 数据签名工具类
 * @author: fearon
 * @create: 2018/7/25 11:29
 **/
public class SignatureTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureTools.class);

    /**
     * MD5签名
     */
    public static String signatureMD5(final Map<String, String> source, String key) throws Exception {
        Set<String> keySet = source.keySet();
        String[] map_key = new String[source.size()];
        StringBuilder temp = new StringBuilder();
        // 进行字典排序
        map_key = keySet.toArray(map_key);
        Arrays.sort(map_key);

        int i = 0, length = map_key.length;
        String current_key, value;
        for (; i < length; i++) {
            current_key = map_key[i];
            value = source.get(current_key);

            if (null != value && value.length() > 0) {
                if (i > 0) {
                    temp.append("&");
                }
                temp.append(current_key);
                temp.append("=");
                temp.append(value);
            }
        }

        if (null != key && key.length() > 0) {
            temp.append("&key=");
            temp.append(key);
        }

        LOGGER.info("signatureMD5 : " + temp.toString());

        return MD5Tool.md5Encode(temp.toString()).toUpperCase();
    }

    /**
     * SHA签名
     * SHA-1
     * SHA-256
     * SHA-384
     * SHA-512
     */
    public static String signatureSHA(final Map<String, String> source, String algorithm, String key) throws Exception {
        Set<String> keySet = source.keySet();
        String[] map_key = new String[source.size()];
        StringBuilder temp = new StringBuilder();
        // 进行字典排序
        map_key = keySet.toArray(map_key);
        Arrays.sort(map_key);

        int i = 0, length = map_key.length;
        String current_key, value;
        for (; i < length; i++) {
            current_key = map_key[i];
            value = source.get(current_key);

            if (null != value && value.length() > 0) {
                if (i > 0) {
                    temp.append("&");
                }
                temp.append(current_key);
                temp.append("=");
                temp.append(value);
            }
        }

        boolean needKey = false;
        if (null != key && key.length() > 0) {
            temp.append("&key=");
            temp.append(key);

            needKey = true;
        }

        LOGGER.info("signatureSHA : " + temp.toString());

        if (algorithm.equalsIgnoreCase("HMAC-SHA256")) {
            if (needKey)
                return HMACSHA256(temp.toString(), key);

            return HMACSHA256(temp.toString(), "");
        } else {
            String message = temp.toString();

            MessageDigest messageDigest;
            byte[] message_bytes = new byte[0];
            try {
                messageDigest = MessageDigest.getInstance(algorithm);
                message_bytes = messageDigest.digest(message.getBytes("utf-8"));
            } catch (Exception e) {
                LOGGER.error("数据加密异常！");
            }

            String byte_str = byteToStr(message_bytes);
            return null == byte_str ? null : byte_str.toUpperCase();
        }
    }

    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String nonceString() {
        Long random_value = new CheckCodeCreater(
                System.currentTimeMillis()
        ).codeCreate(100000000, 999999999);

        return byteToStr(random_value.toString().getBytes());
    }

    /**
     * 字节数组转换为字符串
     */
    public static String byteToStr(final byte[] byte_array) {
        if (null == byte_array || byte_array.length == 0)
            return null;

        StringBuilder digest = new StringBuilder();
        int i = 0, length = byte_array.length;
        for (; i < length; i++) {
            digest.append(byteToHexStr(byte_array[i]));
        }

        return digest.toString();
    }

    /**
     * 字节转换为字符串
     */
    public static String byteToHexStr(final byte single_byte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(single_byte >>> 4) & 0X0F];
        tempArr[1] = digit[single_byte & 0X0F];

        return new String(tempArr);
    }

    /**
     * 校验签名是否正常
     *
     * @param data
     * @param algorithm
     * @param key
     * @return
     */
    public static boolean isRealSignature(Map<String, String> data, String algorithm, String key, String sign)
            throws Exception {
        if(null == sign || sign.trim().length() <= 0)
            throw new NullPointerException("被校验的签名不可为空！");

        String current_sign = null;

        if (algorithm.equalsIgnoreCase("MD5"))
            current_sign = signatureMD5(data, key);
        else
            current_sign = signatureSHA(data, algorithm, key);

        return sign.equalsIgnoreCase(current_sign);
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> source = new HashMap<>();
        source.put("name", "fearon");
        source.put("age", "24");
        source.put("sex", "male");

        /*--------------------------ASCII 排序测试------------------------------*/
        Set<String> keySet = source.keySet();
        String[] map_key = new String[source.size()];
        map_key = keySet.toArray(map_key);
        Arrays.sort(map_key);
        for (int i = 0; i < 3; i++) {
            System.out.println(map_key[i]);
        }

        /*--------------------------MD5 签名测试------------------------------*/
        System.out.println(signatureMD5(source, "Miracle"));
        /*--------------------------SHA 签名测试------------------------------*/
        System.out.println(signatureSHA(source, "SHA-256", "PLljAopwYJMWgEnAOaBlxRFWjI7tqMyh"));
        /*--------------------------随机字符串--------------------------------*/
        System.out.println(nonceString());
    }
}
