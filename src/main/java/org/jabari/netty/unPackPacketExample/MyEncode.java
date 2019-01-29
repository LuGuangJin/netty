package org.jabari.netty.unPackPacketExample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Description:
 * 自定义编码器。
 * @author Jabari Lu
 * @date 2018/12/16
 */
public class MyEncode extends MessageToByteEncoder<MyProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        System.out.println("--------MyEncode.encode() invoked!");
        int length = msg.getLength();
        byte[] content = msg.getContent();

        out.writeInt(length);
        out.writeBytes(content);

    }
}
