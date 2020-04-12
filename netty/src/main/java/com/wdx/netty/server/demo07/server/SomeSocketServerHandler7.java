package com.wdx.netty.server.demo07.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class SomeSocketServerHandler7 extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            String eventDes = "";
            switch (idleStateEvent.state()) {
                case READER_IDLE: eventDes = "读空闲超时";break;
                case WRITER_IDLE: eventDes = "写空闲超时";break;
                case ALL_IDLE: eventDes = "读写空闲超时";break;
            }
            System.out.println(ctx.channel().remoteAddress() + "：, " + eventDes);
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
