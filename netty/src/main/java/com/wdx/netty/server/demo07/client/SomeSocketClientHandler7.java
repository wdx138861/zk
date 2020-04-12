package com.wdx.netty.server.demo07.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SomeSocketClientHandler7 extends ChannelInboundHandlerAdapter {

    private ScheduledFuture schedule;

    private GenericFutureListener listener;

    private Bootstrap bootstrap;

    public SomeSocketClientHandler7(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受服务端发送的消息：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendHeartBeat(ctx.channel());
    }

    private void sendHeartBeat(Channel channel) {
        int interval = new Random().nextInt(7) + 1;
        System.out.println(interval + "秒会像服务端发送心跳");

        schedule = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println("向服务端发送心跳");
                channel.writeAndFlush("ping");
            } else {
                System.out.println("与服务端的连接已经关闭");
                //防止关闭递归调用
                schedule.removeListener(listener);
                //重连
                System.out.println("重新连接服务器...");
                bootstrap.connect("127.0.0.1", 666);
            }
        }, interval, TimeUnit.SECONDS);

        //向定时器添加监听器
        listener = (future) -> {
            //自此发送心跳
            sendHeartBeat(channel);
        };

        schedule.addListener(listener);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
