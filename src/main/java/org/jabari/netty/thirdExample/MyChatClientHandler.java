package org.jabari.netty.thirdExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/10/24
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
    }
}
