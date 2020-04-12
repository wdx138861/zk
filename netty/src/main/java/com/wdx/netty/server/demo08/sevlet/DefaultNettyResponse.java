package com.wdx.netty.server.demo08.sevlet;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class DefaultNettyResponse implements NettyResponse {

    private HttpRequest request;

    private ChannelHandlerContext ctx;

    public DefaultNettyResponse(HttpRequest request, ChannelHandlerContext ctx) {
        this.request = request;
        this.ctx = ctx;
    }

    @Override
    public void write(String content) throws Exception {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaders.Names.CONTENT_TYPE, "text/json");
        headers.set(HttpHeaders.Names.CONTENT_TYPE, response.content().readableBytes());
        headers.set(HttpHeaders.Names.EXPIRES, 0);
        if (HttpHeaders.isKeepAlive(request)) {
            headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.writeAndFlush(response);
    }
}
