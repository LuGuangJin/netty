package org.jabari.netty.unPackPacketExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.jabari.netty.stickyPacketExample.TimeClientHandler;

/**
 * Description:
 *  Netty时间服务器的客户端
 * @author Jabari Lu
 * @date 2018/12/16
 */
public class MyClient {

    public void connect(int port,String host) throws InterruptedException {
        // 创建客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new MyClientInitializerHandler());
            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(host,port).sync();
            //等侍客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程组资源
            group.shutdownGracefully();
        }


    }



    private class MyClientInitializerHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new MyDecoder());
            pipeline.addLast(new MyEncode());
            pipeline.addLast(new MyClientHandler());
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
        new MyClient().connect(port,"127.0.0.1");
    }

}
