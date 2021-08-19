package nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO客户端连接端
 *
 * @author: ZOUFANQI
 * @create: 2021-08-19 15:05
 **/
public class NIOClientHandler implements Runnable {
    private final String serverHost;
    private final int serverPort;
    private final Selector selector;
    private final SocketChannel channel;
    private volatile boolean started;

    public NIOClientHandler(String serverHost, int serverPort) throws IOException {
        this.serverHost = serverHost;
        this.serverPort = serverPort;

        selector = Selector.open();
        channel = SocketChannel.open();
        // 如果为true，则此通道将被置于阻塞模式；如果为false，则此通道将被置于非阻塞模式
        channel.configureBlocking(false);
        started = true;
        System.out.println("客户端已启动，请输入消息内容...");
    }

    @Override
    public void run() {
        try {
            doConnect();

            // 循环遍历selector
            while (started) {
                // 阻塞方法,当至少一个注册的事件发生的时候就会继续
                selector.select();
                // 获取当前有哪些事件可以使用
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    // 必须首先将处理过的 SelectionKey 从选定的键集合中删除。
                    // 如果我们没有删除处理过的键，那么它仍然会在事件集合中以一个激活的键出现，这会导致我们尝试再次处理它。
                    it.remove();
                    try {
                        handleInput(channel, key);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 具体事件处理方法
     *
     * @param channel
     * @param key
     * @throws IOException
     */
    private void handleInput(SocketChannel channel, SelectionKey key) throws IOException {
        if (!key.isValid()) return;

        // 获得关心当前事件的channel
        SocketChannel sc = (SocketChannel) key.channel();
        // 处理连接就绪事件，但是三次握手未必就成功了，所以需要等待握手完成和判断握手是否成功
        if (key.isConnectable()) {
            // finishConnect的主要作用就是确认通道连接已建立，方便后续IO操作（读写）不会因连接没建立而导致NotYetConnectedException异常。
            if (sc.finishConnect()) {
                // 连接既然已经建立，当然就需要注册读事件，写事件一般是不需要注册的。
                channel.register(selector, SelectionKey.OP_READ);
            } else System.exit(-1);
        }

        // 处理读事件，也就是当前有数据可读
        if (key.isReadable()) {
            // 创建ByteBuffer，并开辟一个1k的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 将通道的数据读取到缓冲区，read方法返回读取到的字节数
            int readBytes = sc.read(buffer);
            if (readBytes > 0) {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String result = new String(bytes, "UTF-8");
                System.out.println("客户端收到消息：" + result);
            } else if (readBytes < 0) {
                // 链路已经关闭，释放资源
                key.cancel();
                sc.close();
            }

        }
    }

    /**
     * 连接服务端
     */
    private void doConnect() throws IOException {
        /*
          如果此通道处于非阻塞模式，则调用此方法将启动非阻塞连接操作。
          如果连接马上建立成功，则此方法返回true，否则，此方法返回false。
          因此我们必须关注连接就绪事件，并通过调用finishConnect方法完成连接操作。
         */
        if (channel.connect(new InetSocketAddress(serverHost, serverPort))) {
            // 连接成功，关注读事件
            channel.register(selector, SelectionKey.OP_READ);
        } else {
            // 关注连接事件
            channel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 写数据对外暴露的API
     *
     * @param msg
     * @throws IOException
     */
    public void sendMsg(String msg) throws IOException {
        doWrite(channel, msg);
    }

    private void doWrite(SocketChannel sc, String request) throws IOException {
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);
    }
}
