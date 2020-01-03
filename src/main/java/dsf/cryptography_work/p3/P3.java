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

    static String MAC_write_secret = "董少飞";

    static String key;

    byte pad_1 = 0x36, pad_2 = 0x5C;
    byte[] pad_1s = new byte[40], pad_2s = new byte[40];
    {
        for (int i=0; i<40; i++) {
            pad_1s[i] = pad_1;
            pad_2s[i] = pad_2;
        }
    }

    public static void main(String[] args) throws Exception {

        key = AESUtil.generateKey();

        byte[] encrypt = send(new StringBuilder("I am 董少飞"));

    }

    public static byte[] send (StringBuilder message) throws Exception {

        StringBuilder sb, segment;

        // 分段
        if (message.length() > bodyMaxSize/2) {

            sb = new StringBuilder(message.substring(0, bodyMaxSize/2));
            message.delete(0, bodyMaxSize/2);
        } else {

            sb = new StringBuilder(message.toString());
            message.delete(0, message.length());
        }
        segment = new StringBuilder(sb);

        // 消息认证码
        int bodyLength = sb.length();
        sb.append(MAC_write_secret)
                .append(pad_2)
                .append(Arrays.toString(DigestUtils.sha1(MAC_write_secret + String.valueOf(pad_1s) + 0)))
                .append(0)
                .append(bodyLength*2);
        String sha1Hex = DigestUtils.sha1Hex(sb.toString());

        // 加密
        int length = (bodyLength + sha1Hex.length()) * 2;
        byte[] padding = null;
        if (length / 8 * 8 != length) {
            int paddingLength = 8 - length & 8 - 1;
            padding = new byte[paddingLength];
            padding[paddingLength-1] = (byte) (paddingLength - 1);
        }

        segment.append(sha1Hex)
                .append(padding);
        return Base64.decodeBase64(AESUtil.encrypt(key, segment.toString()));
    }

    public String receive (byte[] encryption) throws Exception {


    }
}
