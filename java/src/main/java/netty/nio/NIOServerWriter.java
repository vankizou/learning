package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端2（读写）
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 14:26
 **/
public class NIOServerWriter {
    /**
     * 服务启动端口
     */
    public static final int PORT = 12345;

    private static Selector selector;
    private static volatile boolean started;

    public static void main(String[] args) throws IOException {
        /**
         * 开启服务端，注册接收事件
         */
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // 如果为true，则此通道将被置于阻塞模式；如果为false，则此通道将被置于非阻塞模式
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        started = true;
        System.out.println("服务端已启动，端口：" + PORT);

        /**
         * 循环遍历selector
         */
        while (started) {
            // 阻塞，只有当至少有一个注册事件时才会继续
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                try {
                    handleInput(key);
                } catch (Exception e) {
                    if (key != null) {
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                }
            }
        }
        if (selector != null) {
            selector.close();
        }
    }

    private static void handleInput(SelectionKey key) throws IOException {
        if (!key.isValid()) {
            return;
        }

        if (key.isAcceptable()) {
            /**
             * 处理新接入的请求消息
             */
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            System.out.println("======= 建立连接 =======");
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        }

        if (key.isReadable()) {
            /**
             * 读消息
             */
            System.out.println("====== socket channel 数据准备完成，可以去读取 ======");
            SocketChannel sc = (SocketChannel) key.channel();
            // 创建ByteBuffer，并开辟一个1M的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读取请求码流，返回读取到的字节数
            int readBytes = sc.read(buffer);
            // 读取到字节，对字节进行编解码
            if (readBytes > 0) {
                // 将缓冲区当前的limit设置为position,position=0，
                // 用于后续对缓冲区的读取操作
                buffer.flip();
                // 根据缓冲区可读字节数创建字节数组
                byte[] bytes = new byte[buffer.remaining()];
                // 将缓冲区可读字节数组复制到新建的数组中
                buffer.get(bytes);
                String message = new String(bytes, "UTF-8");
                System.out.println("服务器收到消息：" + message);
                // 发送应答消息
                doWrite(sc, String.format("你发的消息【%s】服务端已收到！", message));
            } else if (readBytes < 0) {
                /**
                 * 链路已经关闭，释放资源
                 */
                key.cancel();
                sc.close();
            }
        }

        if (key.isWritable()) {
            System.out.println("---- writeable ----");
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer att = (ByteBuffer) key.attachment();
            if (att.hasRemaining()) {
                int count = sc.write(att);
                System.out.println("write:" + count + "byte ,hasR:" + att.hasRemaining());
            } else {
                // 表示只对读事件感兴趣，其他的事件都注销
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    /**
     * 发送应答消息
     *
     * @param channel
     * @param response
     * @throws IOException
     */
    private static void doWrite(SocketChannel channel, String response)
            throws IOException {
        // 将消息编码为字节数组
        byte[] bytes = response.getBytes();
        // 根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        // 将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        // flip操作，切换到读
        writeBuffer.flip();
        // 发送缓冲区的字节数组
        channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, writeBuffer);
    }

    public static void stop() {
        started = false;
    }
}
