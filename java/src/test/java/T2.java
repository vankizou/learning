import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-09 11:16
 **/
public class T2 {
    public static void main(String[] args) throws InterruptedException {
//        Map<User, String> map = new ConcurrentHashMap<>();
//        map.put(new User(), "123");
//        map.put(null, "null");
//        System.out.println(map);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    private void a() {
        System.out.println(new User().hashCode());
        new User.User2().a();
    }

    static class User {
         static class User2 {
            public void a() {
                System.out.println("xxxxx");
            }
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
