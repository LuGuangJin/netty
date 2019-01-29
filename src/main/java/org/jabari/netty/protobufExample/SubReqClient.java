package org.jabari.netty.protobufExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.jabari.protobuf.SubscribeRespProto;

/**
 * Description:
 * Protobuf版本的图书订购客户端的开发
 * @author Jabari Lu
 * @date 2018/12/8
 */
public class SubReqClient {

    public void connect(int port , String host) throws  Exception {
        // 配置客户端的NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            pipeline.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new SubReqClientHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws Exception {
        int port = 8899;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 釆用默认值
            }
        }
        new SubReqClient().connect(port, "127.0.0.1");
    }

}
