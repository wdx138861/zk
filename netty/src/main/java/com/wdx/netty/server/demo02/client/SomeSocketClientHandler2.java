package com.wdx.netty.server.demo02.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 泛型的类型与服务端的消息是一直
 */
public class SomeSocketClientHandler2 extends SimpleChannelInboundHandler<String> {

    //msg的消息类型与类中的泛型是一致的
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("ctx = " + ctx.channel().remoteAddress() + ", msg = " + msg);
        ctx.channel().writeAndFlush("from client: " + LocalDateTime.now());
        TimeUnit.SECONDS.sleep(2);
    }

    //当channel被激活时触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from client: begin talking");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



}
