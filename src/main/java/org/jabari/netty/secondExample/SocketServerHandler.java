package org.jabari.netty.secondExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/10/22
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(" client address : " + ctx.channel().remoteAddress() + ", msg : " + msg);

        ctx.channel().writeAndFlush(" from server : " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
