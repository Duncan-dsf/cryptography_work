package dsf.cryptography_work.base;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.base
 * @Description: TODO
 * @date Date : 2020年01月03日 12:43
 */
public class DESUtil {

    public static String encrypt(String key, String message) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(key);
        byte[] messageBytes = message.getBytes();
        byte[] encrypt = encrypt(keyBytes, messageBytes);
        return Base64.encodeBase64String(encrypt);
    }

    public static byte[] encrypt(byte[] key, byte[] message) throws Exception {

        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = factory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }


    public static byte[] decrypt(byte[] key, byte[] cipherText) throws Exception {

        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = factory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(cipherText);
    }

    public static String decrypt(String key, String cipherText) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(key);
        byte[] cipherBytes = Base64.decodeBase64(cipherText);
        byte[] messageBytes = decrypt(keyBytes, cipherBytes);
        return new String(messageBytes);
    }

    public static String generateKey() throws Exception {

        return Base64.encodeBase64String(generateBytesKey());
    }

    public static byte[] generateBytesKey() throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static void main(String[] args) throws Exception {

        String key = generateKey();
        System.out.println("key : " + key);
        String message = "I am 董少飞";

        String encrypt = encrypt(key, message);
        System.out.println("encrypt : " + encrypt);

        System.out.println(decrypt(key, encrypt));
    }
}
