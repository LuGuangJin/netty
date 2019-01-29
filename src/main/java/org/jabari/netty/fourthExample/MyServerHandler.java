package org.jabari.netty.fourthExample;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/10/26
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String state = null;

            switch (event.state()) {
                case READER_IDLE:
                    state = "读空闲";
                    break;
                case WRITER_IDLE:
                    state = "写空闲";
                    break;
                case ALL_IDLE:
                    state = "读写空闲";
                    break;
            }
            ctx.writeAndFlush("---超时，事件："+state);
            ctx.channel().close();
        }
    }
}
