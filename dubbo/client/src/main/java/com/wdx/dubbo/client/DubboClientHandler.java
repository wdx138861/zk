package com.wdx.dubbo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DubboClientHandler extends SimpleChannelInboundHandler<Object> {

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        result = obj;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
