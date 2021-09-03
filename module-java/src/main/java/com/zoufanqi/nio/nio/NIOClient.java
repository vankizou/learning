package com.zoufanqi.nio.nio;

import java.io.IOException;
import java.util.Scanner;

/**
 * 基于jdk的NIO客户端
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 15:26
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        NIOClientHandler client = new NIOClientHandler("127.0.0.1", 12345);
        new Thread(client).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            client.sendMsg(scanner.next());
        }
    }
}
