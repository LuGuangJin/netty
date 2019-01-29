package org.jabari.netty.stickyPacketExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description:
 *  Netty时间服务器的服务端
 * @author Jabari Lu
 * @date 2018/12/9
 */
public class TimeServer {

    public void bind(int port) throws InterruptedException {
        // 创建服务器NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new TimeServerInitializerHandler());
            //绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println(TimeServer.class.getName() + " started and listen on " + future.channel().localAddress());
            //等侍服务器监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程组资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }



    private class TimeServerInitializerHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new TimeServerHandler());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int port = 8899;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 使用默认值
            }
        }
        new TimeServer().bind(port);
    }

}
