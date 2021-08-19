package nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 基于netty实现的NIO客户端
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 19:18
 **/
public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientHandler());
        System.out.println("客户端启动成功！");
        bootstrap.connect("127.0.0.1", 12345).addListener(f -> {
            if (f.isSuccess()) {
                System.out.println("连接成功！");
            } else {
                System.out.println("连接失败！");
            }
        });
    }
}
