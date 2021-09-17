package com.zoufanqi.collection;

import java.util.Collections;
import java.util.Objects;

/**
 * @author: ZOUFANQI
 * @create: 2021-09-10 10:01
 **/
public class T {
    public static void main(String[] args) {
        System.out.println(123445 % 64);
        Integer a = 128;
        Integer b = 128;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(23 % 2);
        System.out.println(23 & (2 - 1));
        System.out.println(Objects.equals(a, b));
    }
}
