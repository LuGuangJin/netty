package org.jabari.netty.secondExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/10/22
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Server address : " + ctx.channel().remoteAddress());
        System.out.println("client msg : " + msg);

        ctx.writeAndFlush(" from client : " + LocalDateTime.now());
        /*try {
            Thread.sleep(13000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
//        ctx.channel().close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---------channelActive!!!!!!!");
        ctx.writeAndFlush("...客户端向服务端问好!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
