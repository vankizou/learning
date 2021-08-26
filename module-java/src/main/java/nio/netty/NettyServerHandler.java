package nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-19 19:38
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端读到数据以后，就会执行
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("服务端接收到数据：" + in.toString(CharsetUtil.UTF_8));
        ctx.write(Unpooled.copiedBuffer(String.format("\n服务器接收到了你发送过来的数据【%s】", in.toString(CharsetUtil.UTF_8)).getBytes()));
        ctx.fireChannelRead(msg);
    }

    /**
     * 服务端读取完成网络数据后的处理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发生异常后的处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
