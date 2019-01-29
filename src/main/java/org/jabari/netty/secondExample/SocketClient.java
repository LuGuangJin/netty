package org.jabari.netty.secondExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Description:
 *  Socket客户端
 * @author Jabari Lu
 * @date 2018/10/22
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new SocketClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost",8899).sync();
            System.out.println("----Socket客户端 连接了！");

            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
