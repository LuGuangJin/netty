package org.jabari.netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Description:
 * Netty服务器。
 *
 * @author Jabari Lu
 * @date 2018/10/19
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            String msg = String.format("------------Server于[%s %s]成功启动了：", LocalDate.now(),LocalTime.now());
            System.out.println(msg);
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
