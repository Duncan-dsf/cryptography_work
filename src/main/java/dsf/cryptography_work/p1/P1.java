package dsf.cryptography_work.p1;

import dsf.cryptography_work.base.DESUtil;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work
 * @Description: TODO
 * @date Date : 2020年01月03日 12:39
 */
public class P1 {

    public static void main(String[] args) throws Exception {

        Alice alice = new Alice();
        Bob bob = new Bob();
        TTP ttp = new TTP();
        ttp.keyA = alice.ttpKey;
        ttp.keyB = bob.ttpKey;

        // alice生成随机密钥并加密
        alice.keyAB = DESUtil.generateKey();
        String keyABEncryptByKeyA = DESUtil.encrypt(alice.ttpKey, alice.keyAB);

        // 发送给TTP，TTP解密并用bob.keyTTP加密
        String keyAB = DESUtil.decrypt(ttp.keyA, keyABEncryptByKeyA);
        String keyABEncryptByKeyB = DESUtil.encrypt(ttp.keyB, keyAB);

        //发送给bob，bob解密
        bob.keyAB = DESUtil.decrypt(bob.ttpKey, keyABEncryptByKeyB);
        System.out.println(alice.keyAB.equals(bob.keyAB));
    }
}
