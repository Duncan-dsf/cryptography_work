package dsf.cryptography_work.base;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.base
 * @Description: TODO
 * @date Date : 2020年01月03日 17:37
 */
public class AESUtil {

    public static byte[] generateBytesKey() throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static String generateKey() throws Exception {

        return Base64.encodeBase64String(generateBytesKey());
    }

    public static byte[] encrypt (byte[] key, byte[] message) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(message);
    }

    public static String encrypt(String key, String message) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(key);
        byte[] encryptBytes = encrypt(keyBytes, message.getBytes());
        return Base64.encodeBase64String(encryptBytes);
    }

    public static byte[] decrypt(byte[] key, byte[] encryption) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encryption);
    }

    public static String decrypt(String key, String encryption) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(key);
        byte[] decrypt = decrypt(keyBytes, Base64.decodeBase64(encryption));
        return new String(decrypt);
    }

    public static void main(String[] args) throws Exception {

        String key = generateKey();
        String message = "I am 董少飞";
        String encrypt = encrypt(key, message);
        System.out.println(decrypt(key, encrypt));
    }
}
