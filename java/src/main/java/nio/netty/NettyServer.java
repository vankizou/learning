package nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 基于netty实现的NIO服务端
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 19:11
 **/
public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap boostrap = new ServerBootstrap();
        boostrap.group(workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true) // 2小时无数据激活心跳机制
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("服务端初始化...");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        System.out.println("有客户端连接...");
                        ChannelPipeline ph = channel.pipeline();
                        ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
                    }
                });
        try {
            ChannelFuture channel = boostrap.bind(12345).sync();
            System.out.println("服务端已启动！");

            channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            workGroup.shutdownGracefully();
        }

    }
}
