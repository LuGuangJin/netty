package org.jabari.netty.stickyPacketExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/9
 */
public class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int counter;
    private String reqStr = "Client Send Msg";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        String lineSeparator = System.getProperty("line.separator");
        for (int i = 0; i < 10; i++) {
            byte[] bytes = reqStr.concat(lineSeparator).getBytes(CharsetUtil.UTF_8);
            message = Unpooled.buffer(bytes.length);
            message.writeBytes(bytes);
            ctx.writeAndFlush(message);
        }
    }



    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] reqBytes = new byte[msg.readableBytes()];
        msg.readBytes(reqBytes);
        String body = new String(reqBytes,CharsetUtil.UTF_8);
        String receiveMsg = String.format("Client receive : %s ; the counter is : %d.", body, ++counter);
        System.out.println(receiveMsg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//释放资源
    }


}
