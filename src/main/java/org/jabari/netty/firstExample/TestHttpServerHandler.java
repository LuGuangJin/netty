package org.jabari.netty.firstExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Description:
 *  请求响应的处理器。
 * @author Jabari Lu
 * @date 2018/10/19
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(msg.getClass());

        System.out.println("RemoteAddress:" + ctx.channel().remoteAddress());

        Thread.sleep(8000);

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

            System.out.println("---请求方法名:" + request.method().name());
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("----请求favicon.ico");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("Hello Netty!", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
//            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
//            response.headers().set(HttpHeaderNames.ACCEPT_CHARSET, CharsetUtil.UTF_8);

            ctx.writeAndFlush(response);

            ctx.channel().close();
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("---handlerAdded--- at [%s %s]", LocalDate.now(),LocalTime.now());
        System.out.println(msg);
        super.handlerAdded(ctx);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("---channelUnregistered--- at [%s %s]", LocalDate.now(),LocalTime.now());
        System.out.println(msg);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("---channelInactive--- at [%s %s]", LocalDate.now(),LocalTime.now());
        System.out.println(msg);
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("---channelActive--- at [%s %s]", LocalDate.now(),LocalTime.now());
        System.out.println(msg);
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("---channelRegistered--- at [%s %s]", LocalDate.now(),LocalTime.now());
        System.out.println(msg);
        super.channelRegistered(ctx);
    }
}
