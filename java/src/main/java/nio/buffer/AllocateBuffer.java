package nio.buffer;

import java.nio.ByteBuffer;

/**
 * Buffer的分配
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 13:52
 **/
public class AllocateBuffer {
    public static void main(String[] args) {
        System.out.println("================= before allocate: " + Runtime.getRuntime().freeMemory());

        /**
         * 堆上分配
         */
        ByteBuffer buffer = ByteBuffer.allocate(1024000);
        System.out.println("buffer = " + buffer);
        System.out.println("================= after allocate: " + Runtime.getRuntime().freeMemory());

        /**
         * 直接内存分配（JVM外的内存）
         */
        ByteBuffer direct = ByteBuffer.allocateDirect(1024000);
        System.out.println("direct = " + direct);
        System.out.println("================= after direct allocate: " + Runtime.getRuntime().freeMemory());

        System.out.println("---------- Test wrap --------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);
    }
}
