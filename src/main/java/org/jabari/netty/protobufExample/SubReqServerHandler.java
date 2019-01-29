package org.jabari.netty.protobufExample;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.jabari.protobuf.SubscribeReqProto;
import org.jabari.protobuf.SubscribeRespProto;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/8
 */
@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;

        if ("Jabari Lu".equalsIgnoreCase(req.getUserName())) {
            System.out.println(String.format("Server accept client subscribe req :[%s]", req.toString()));
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }

    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Just test use protobuf with netty!!!");
        return builder.build();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//发生异常，关闭链路
    }
}
