package dsf.cryptography_work.p2;

import dsf.cryptography_work.base.DESUtil;
import dsf.cryptography_work.p1.Alice;
import dsf.cryptography_work.p1.Bob;
import dsf.cryptography_work.p1.TTP;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.p2
 * @Description: TODO
 * @date Date : 2020年01月03日 13:50
 */
public class P2 {

    public static void main(String[] args) throws Exception {

        Alice alice = new Alice();
        Bob bob = new Bob();
        TTP ttp = new TTP();
        ttp.keyA = alice.ttpKey;
        ttp.keyB = bob.ttpKey;

        // ttp生成密钥加密并分发给alice和bob
        String keyAB = DESUtil.generateKey();
        String encryptByKeyA = DESUtil.encrypt(ttp.keyA, keyAB);
        String encryptByKeyB = DESUtil.encrypt(ttp.keyB, keyAB);

        // alice 和 bob解密
        alice.keyAB = DESUtil.decrypt(alice.ttpKey, encryptByKeyA);
        bob.keyAB = DESUtil.decrypt(bob.ttpKey, encryptByKeyB);
        System.out.println(alice.keyAB.equals(bob.keyAB));
    }
}
