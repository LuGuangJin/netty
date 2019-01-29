package org.jabari.netty.unPackPacketExample;

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
public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private int counter;
    private String reqStr = "Client Send Msg";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyProtocol protocol = null;
//        String lineSeparator = System.getProperty("line.separator");
        for (int i = 0; i < 10; i++) {
            protocol = new MyProtocol();
            byte[] bytes = reqStr/*.concat(lineSeparator)*/.getBytes(CharsetUtil.UTF_8);
            protocol.setLength(bytes.length);
            protocol.setContent(bytes);
            ctx.writeAndFlush(protocol);
        }
    }





    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//释放资源
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        String body = new String(content,CharsetUtil.UTF_8);
        String receiveMsg = String.format("Client receive : %s ; the counter is : %d.", body, ++counter);
        System.out.println(receiveMsg);

    }
}
