package com.wdx.netty.server.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 服务端处理器
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)msg;
            String name = httpRequest.getMethod().name();
            System.out.println("请求方式： " + name);
            String uri = httpRequest.getUri();
            System.out.println("请求URI： " + uri);
            if ("/favicon.ico".equals(uri)) {
                System.out.println("不处理/favicon.ico请求");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("Hello Netty World", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            HttpHeaders headers = response.headers();
            headers.set(HttpHeaders.newEntity("Content-Type"), "text/plain");
            headers.set(HttpHeaders.newEntity("Content-Length"), content.readableBytes());

            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
