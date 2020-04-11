package utils.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 数据签名工具类
 * @author: fearon
 * @create: 2019/5/17 10:01:00
 **/
public class SignatureTools {
    /**
     * MD5签名
     *
     * @param source 明文
     * @param key    秘钥
     */
    public static String signatureMD5(final Map<String, String> source, final String key) throws Exception {
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

        return MD5Tool.md5Encode(temp.toString()).toUpperCase();
    }

    /**
     * SHA签名
     *
     * @param source    明文
     * @param algorithm 签名算法（HMAC-SHA256、SHA-1、SHA-256、SHA-384、SHA-512）
     * @param key       秘钥
     */
    public static String signatureSHA(final Map<String, String> source, final String algorithm, final String key)
            throws Exception {
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

        if (algorithm.equalsIgnoreCase("HMAC-SHA256")) {
            if (needKey)
                return HMacSHA256(temp.toString(), key);

            return HMacSHA256(temp.toString(), "");
        } else {
            String message = temp.toString();

            MessageDigest messageDigest;
            byte[] message_bytes = null;
            messageDigest = MessageDigest.getInstance(algorithm);
            message_bytes = messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));

            String byte_str = byteArrayToByteStr(message_bytes);
            return null == byte_str ? null : byte_str.toUpperCase();
        }
    }

    /**
     * 生成 HMacSHA256
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static String HMacSHA256(final String data, final String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param _hex_string 十六进制字符串
     */
    public static byte[] byteStrToByteArray(String _hex_string) {
        // 去除可能存在的空格
        _hex_string = _hex_string.replaceAll(" ", "");
        /*
          字节数组在保存时会被转换为八位二进制所代表的十进制数
          即原本的十六进制字符串中，两个十六进制字符才能对应一个字节元素，整体长度减少一半
         */
        final byte[] byteArray = new byte[_hex_string.length() / 2];
        int _char_index = 0;
        for (int _byte_index = 0; _byte_index < byteArray.length; _byte_index++) {
            // 高位
            byte high = (byte) (Character.digit(_hex_string.charAt(_char_index), 16) & 0xff);
            // 低位
            byte low = (byte) (Character.digit(_hex_string.charAt(_char_index + 1), 16) & 0xff);
            // 合并高低位到一个字节元素中
            byteArray[_byte_index] = (byte) (high << 4 | low);
            _char_index += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转换为字符串
     */
    public static String byteArrayToByteStr(final byte[] byte_array) {
        if (null == byte_array || byte_array.length == 0)
            return null;

        StringBuilder digest = new StringBuilder();
        int i = 0, length = byte_array.length;
        for (; i < length; i++) {
            digest.append(byteToByteStr(byte_array[i]));
        }

        return digest.toString();
    }

    /**
     * 字节转换为字符串
     */
    public static String byteToByteStr(final byte single_byte) {
        char[] digit = {
                '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'
        };
        char[] tempArr = new char[2];
        tempArr[0] = digit[(single_byte >>> 4) & 0x0F];
        tempArr[1] = digit[single_byte & 0x0F];

        return new String(tempArr);
    }

    /**
     * 校验签名是否正常
     *
     * @param data      明文
     * @param algorithm 签名算法（MD5、HMAC-SHA256、SHA-1、SHA-256、SHA-384、SHA-512）
     * @param key       秘钥
     * @param sign      密文
     * @return
     */
    public static boolean isRealSignature(final Map<String, String> data, final String algorithm, final String key,
                                          final String sign) throws Exception {
        if (null == sign || sign.trim().length() <= 0)
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

        String _source_string = "Miracle";
        /*-------------------------- MD5 签名测试 ------------------------------*/
        System.out.println(signatureMD5(source, _source_string));
        /*-------------------------- SHA 签名测试 ------------------------------*/
        System.out.println(signatureSHA(source, "SHA-256", "PLljAopwYJMWgEnAOaBlxRFWjI7tqMyh"));
        /*-------------------------- 字节数组转十六进制字符串测试 ------------------------------*/
        String _hex_string = byteArrayToByteStr(_source_string.getBytes());
        System.out.println(_hex_string);
        /*-------------------------- 十六进制字符串转字节数组测试 ------------------------------*/
        byte[] bytes = byteStrToByteArray(_hex_string);
        System.out.println(new String(bytes));

        System.out.println(byteToByteStr((byte) 38));
    }
}
