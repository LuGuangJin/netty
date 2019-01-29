package org.jabari.netty.unPackPacketExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/16
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务器端接收到的数据：");
        System.out.println("长度：" + length);
        System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
        System.out.println("服务器端接收到的消息数量：" + (++this.count));


        String responseMsg = UUID.randomUUID().toString();
        byte[] respBytes = responseMsg.getBytes(Charset.forName("utf-8"));
        MyProtocol protocol = new MyProtocol();
        protocol.setLength(respBytes.length);
        protocol.setContent(respBytes);
        ctx.writeAndFlush(protocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
