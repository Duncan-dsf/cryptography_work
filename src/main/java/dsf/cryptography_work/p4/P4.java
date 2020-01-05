package dsf.cryptography_work.p4;

import java.io.File;
import java.io.IOException;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.p4
 * @Description: TODO
 * @date Date : 2020年01月05日 15:25
 */
public class P4 {

    public static void main(String[] args) throws IOException {

        FileSystem.authorization("dsf.txt", "dsf", 2+1);
        byte[] bytes = FileSystem.read("dsf.txt", "dsf");
        System.out.println(new String(bytes));

        String message = "I am dsf";
        int ret = FileSystem.write("dsf.txt", "dsf", message.getBytes());
        System.out.println(ret);
    }
}
