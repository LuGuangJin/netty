package org.jabari.netty.stickyPacketExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/9
 */
public class TimeServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int counter;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String lineSeparator = System.getProperty("line.separator");
        byte[] req = new byte[msg.readableBytes()];
        msg.readBytes(req);
        String msgBody = new String(req, UTF_8).substring(0,req.length - lineSeparator.length());
        String cont = String.format("Server receive : %s ; the counter is : %d.", msgBody, ++counter);
        System.out.println(cont);
        String currentTimeStr = UUID.randomUUID().toString().concat(lineSeparator);
        ByteBuf resp = Unpooled.copiedBuffer(currentTimeStr.getBytes(Charset.forName("utf-8")));
        ctx.writeAndFlush(resp);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        ctx.close();
    }



}
