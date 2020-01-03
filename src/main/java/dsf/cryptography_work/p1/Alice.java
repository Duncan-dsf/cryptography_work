package dsf.cryptography_work.p1;

import dsf.cryptography_work.base.DESUtil;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.base
 * @Description: TODO
 * @date Date : 2020年01月03日 12:36
 */
public class Alice {

    public String ttpKey;
    public String keyAB;

    public Alice () throws Exception {

        ttpKey = DESUtil.generateKey();
    }
}
