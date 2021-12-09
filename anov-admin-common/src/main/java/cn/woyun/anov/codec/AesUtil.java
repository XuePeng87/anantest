package cn.woyun.anov.codec;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * AES工具类。
 *
 * @author xuepeng
 */
public class AesUtil {

    private static final String KEY = "anov@2019:helloX";
    private static final String IV = "anov@2020:helloX";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private AesUtil() {
    }

    public static String decrypt(String context) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(context);
        byte[] bytes = cipherFilter(decode);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private static byte[] cipherFilter(byte[] context) {
        Key secretKeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(context);
    }

}
