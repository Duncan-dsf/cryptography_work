package dsf.cryptography_work.p3;

import dsf.cryptography_work.base.AESUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.p3
 * @Description: TODO
 * @date Date : 2020年01月03日 16:59
 */
public class P3 {

    static int bodyMaxSize = 1024 * 16;

    static byte[] MAC_write_secret = "董少飞".getBytes();

    static int macLength = 20;

    static int headerLength = 4;

    static String key;

    static byte pad_1 = 0x36, pad_2 = 0x5C;
    static byte[] pad_1s = new byte[40], pad_2s = new byte[40];
    static {
        for (int i=0; i<40; i++) {
            pad_1s[i] = pad_1;
            pad_2s[i] = pad_2;
        }
    }

    public static void main(String[] args) throws Exception {

        key = AESUtil.generateKey();

        byte[] encrypt = send("I am 董少飞");
        String message = receive(encrypt);
        System.out.println(message);
    }

    public static byte[] send (String total) throws Exception {

        String message;

        // 分段
        if (total.length() > bodyMaxSize/2) {

            message = total.substring(0, bodyMaxSize/2);
        } else {

            message = total;
        }

        // 消息认证码
        byte[] messageBytes = message.getBytes();
        byte[] mac = getMAC(messageBytes);

        // 填充
        int bodyLengthWithoutPadding = messageBytes.length + mac.length + 1;
        byte[] padding = null;
        int paddingLength = 8 - bodyLengthWithoutPadding & 7;
        padding = new byte[paddingLength+1];
        padding[paddingLength] = (byte) paddingLength;

        byte[] body = new byte[bodyLengthWithoutPadding + paddingLength];
        System.arraycopy(messageBytes, 0, body, 0, messageBytes.length);
        System.arraycopy(mac, 0, body, messageBytes.length, mac.length);
        System.arraycopy(padding, 0, body, bodyLengthWithoutPadding-1, padding.length);

        // 加密
        byte[] bodyCipher = AESUtil.encrypt(Base64.decodeBase64(key), body);

        // 添加ssl头
        byte[] segment = new byte[headerLength + bodyCipher.length];
        segment[0] = 0;
        segment[1] = 3;
        segment[2] = 0;
        segment[3] = (byte) bodyCipher.length;
        System.arraycopy(bodyCipher, 0, segment, 4, bodyCipher.length);
        return segment;
    }

    public static String receive (byte[] encryption) throws Exception {

        // 解析协议，拿出body，并解密
        byte[] bodyEncryption = new byte[encryption[3]];
        System.arraycopy(encryption, 4, bodyEncryption, 0, encryption[3]);
        byte[] message = AESUtil.decrypt(Base64.decodeBase64(key), bodyEncryption);

        // 去掉填充并解析
        int paddingLength = message[message.length-1];
        byte[] messageBody = new byte[message.length - 1 - paddingLength - macLength],
        macBytes = new byte[macLength];
        System.arraycopy(message, 0, messageBody, 0, messageBody.length);
        System.arraycopy(message, messageBody.length, macBytes, 0, macBytes.length);

        // 校验mac
        byte[] mac = getMAC(messageBody);
        System.out.println(Arrays.equals(mac, macBytes));

        return new String(messageBody);
    }

    public static byte[] getMAC(byte[] message) {

        byte[] bytes = new byte[message.length + pad_2s.length + MAC_write_secret.length + 1 + 1];

        System.arraycopy(MAC_write_secret, 0, bytes, 0, MAC_write_secret.length);
        System.arraycopy(pad_2s, 0, bytes, MAC_write_secret.length, pad_1s.length);
        bytes[MAC_write_secret.length + pad_2s.length] = 0;
        bytes[MAC_write_secret.length + pad_2s.length + 1] = (byte) message.length;
        System.arraycopy(message, 0, bytes, MAC_write_secret.length + pad_2s.length + 1 + 1, message.length);
        return DigestUtils.sha1(bytes);
    }
}
