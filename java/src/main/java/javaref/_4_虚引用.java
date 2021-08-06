package javaref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 就是个虚的
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class _4_虚引用 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // 2. 三个虚引用，1M、2M、3M
        ReferenceQueue rq = new ReferenceQueue();
        PhantomReference<byte[]> pr1 = new PhantomReference<>(new byte[1024 * 1024 * 1], rq);
        PhantomReference<byte[]> pr2 = new PhantomReference<>(new byte[1024 * 1024 * 2], rq);
        PhantomReference<byte[]> pr3 = new PhantomReference<>(new byte[1024 * 1024 * 3], rq);

        /**
         * 3. 每秒一次循环，添加512K数据
         */
//         执行结果：
//        1: pr1地址：null, pr2地址：null, pr3地址：null, b地址：[B@5e2de80c
//        2: pr1地址：null, pr2地址：null, pr3地址：null, b地址：[B@1d44bcfa
//        3: pr1地址：null, pr2地址：null, pr3地址：null, b地址：[B@266474c2
//        4: pr1地址：null, pr2地址：null, pr3地址：null, b地址：[B@6f94fa3e
//        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        at javaref._4_虚引用.main(_4_虚引用.java:57)

        List<byte[]> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            byte[] b = new byte[1024 * 512];
            System.out.println(String.format("%s: pr1地址：%s, pr2地址：%s, pr3地址：%s, b地址：%s", i, pr1.get(), pr2.get(), pr3.get(), b));
            list.add(b);
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
