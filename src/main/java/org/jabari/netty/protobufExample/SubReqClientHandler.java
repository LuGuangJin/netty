package org.jabari.netty.protobufExample;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.jabari.protobuf.SubscribeReqProto;
import org.jabari.protobuf.SubscribeRespProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/8
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("Receive server response : [" + msg + "]");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            SubscribeReqProto.SubscribeReq subReq = createSubReq(i);
            ctx.write(subReq);
        }
        ctx.flush();
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 创建请求。
     * @return
     */
    private SubscribeReqProto.SubscribeReq createSubReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("Jabari Lu");
        builder.setProductName("Protobuf");
        List<String> address = new ArrayList<>();
        address.add("Su Zhou");
        address.add("Kun Shan");
        address.add("Shang Hai");
        builder.addAllAddress(address);
        return builder.build();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();// 发生异常，关闭链路
    }
}
