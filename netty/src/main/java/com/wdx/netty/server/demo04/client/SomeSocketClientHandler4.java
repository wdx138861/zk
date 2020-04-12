package com.wdx.netty.server.demo04.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 泛型的类型与服务端的消息是一直
 */
public class SomeSocketClientHandler4 extends ChannelInboundHandlerAdapter {

    private String message = "Hello world!";

    //当channel被激活时触发一次该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = message.getBytes();
        ByteBuf buf = null;
        for (int i = 0; i < 100; i++) {
            buf = Unpooled.buffer(bytes.length);
            buf.writeBytes(bytes);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



}
