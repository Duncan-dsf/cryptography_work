package dsf.cryptography_work.p4;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : dongshaofei
 * @version V1.0
 * @Project: cryptography_work
 * @Package dsf.cryptography_work.p4
 * @Description: TODO
 * @date Date : 2020年01月05日 15:29
 */
public class FileSystem {

    private static Map<String, Map<String, Integer>> FILE_USER_AUTH = new ConcurrentHashMap<>();

    public static int authorization (String file, String user, int auth) {

        FILE_USER_AUTH.putIfAbsent(file, new ConcurrentHashMap<>());
        Map<String, Integer> userAuthMap = FILE_USER_AUTH.get(file);
        userAuthMap.put(user, auth);
        return 1;
    }

    public static int authentication (String file, String user) {

        return FILE_USER_AUTH.get(file)==null ? 0 :
                FILE_USER_AUTH.get(file).get(user)==null ? 0 :
                        FILE_USER_AUTH.get(file).get(user);
    }

    public static byte[] read (String file, String user) {

        if ((authentication(file, user) & 1) == 1) {
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {

                int available = inputStream.available();
                byte[] text = new byte[available];
                inputStream.read(text);
                return text;
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static int write(@NotNull String file, String user, byte[] text) {

        if ((authentication(file, user) & 2) == 2) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {

                outputStream.write(text);
                return 0;
            } catch (Exception e) {}
        }
        return 1;
    }
}
