package javaref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class _1_强引用 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // 2. 强引用，5M
        byte[] a = new byte[1024 * 1024 * 5];

        /**
         * 3. 每秒一次循环，添加512K数据
         *
         * 在第6次的时候因为内存直接OOM
         */
//        执行结果：
//        1: a地址：[B@5e2de80c, b地址：[B@1d44bcfa
//        2: a地址：[B@5e2de80c, b地址：[B@266474c2
//        3: a地址：[B@5e2de80c, b地址：[B@6f94fa3e
//        4: a地址：[B@5e2de80c, b地址：[B@5e481248
//        5: a地址：[B@5e2de80c, b地址：[B@66d3c617
//        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        at javaref.强引用.main(强引用.java:30)

        List<byte[]> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            byte[] b = new byte[1024 * 512];
            System.out.println(String.format("%s: a地址：%s, b地址：%s", i, a, b));
            list.add(b);
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
