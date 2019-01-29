package org.jabari.netty.fourthExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/10/26
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(5, 10, 15, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
