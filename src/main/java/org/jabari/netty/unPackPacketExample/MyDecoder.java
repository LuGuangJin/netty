package org.jabari.netty.unPackPacketExample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Description:
 *  自定义解码器。
 * @author Jabari Lu
 * @date 2018/12/16
 */
public class MyDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("--------MyDecoder.decode() invoked!");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setLength(length);
        myProtocol.setContent(content);

        out.add(myProtocol);
    }
}
