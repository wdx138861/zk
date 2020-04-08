package com.wdx.netty.server.demo01;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 管道初始化器
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //定义httpRequest的解码和httpResponse的加密
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //定义httpServer的处理类
        pipeline.addLast("httpServerHandler", new HttpServerHandler());
    }
}
