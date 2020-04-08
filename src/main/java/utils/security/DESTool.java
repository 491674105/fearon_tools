package utils.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * DES加密 解密算法
 *
 * @author fearon
 * @date 2019/5/17 10:01:00
 */
public class DESTool {
    private static final String DES = "DES";
    private static final String ENCODE = "UTF-8";
    private static final String DEFAULT_KEY = "_FEARON_MIRACLE_";

    /**
     * 使用 默认key 加密
     *
     * @return String
     */
    public static String encrypt(String data) throws Exception {
        byte[] data_byte = encrypt(data.getBytes(ENCODE), DEFAULT_KEY.getBytes(ENCODE));
        String data_byte_str = new BASE64Encoder().encode(data_byte);
        // 在实际HTTP通信中，符号'+'或者空格会被识别为 '%2B' 导致密文无法被识别，需提前进行一次转换
        if (data_byte_str.indexOf('+') >= 0) {
            data_byte_str = data_byte_str.replace("+", "%2B");
        }
        return data_byte_str;
    }

    /**
     * 使用 默认key 解密
     *
     * @return String
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
        if (data.contains("%2B")) {
            data = data.replace("%2B", "+");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buffer = decoder.decodeBuffer(data);
        return new String(decrypt(buffer, DEFAULT_KEY.getBytes(ENCODE)), ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 密文
     * @param key  秘钥
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), DEFAULT_KEY.getBytes(ENCODE));
        return new BASE64Encoder().encode(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  秘钥
     */
    public static String decrypt(String data, String key) throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 密文
     * @param key  秘钥
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secure_key = keyFactory.generateSecret(desKeySpec);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secure_key, secureRandom);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  秘钥
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secure_key = keyFactory.generateSecret(desKeySpec);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secure_key, secureRandom);
        return cipher.doFinal(data);
    }
}