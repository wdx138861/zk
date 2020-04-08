package com.wdx.netty.server.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Arrays;

/**
 * 服务端启动类
 * NIO与传统的socket最大的不同就是引入了channel和多路复用selector，传统的socket是基于stream（InputStream和OutStream），它是单向的，阻塞的；
 * channel是双工的，可以同时对缓存进行读写，是面向buff的，异步非阻塞的
 */
public class HttpServer {

    public static void main(String[] args) throws InterruptedException {
        //创建父子调度模块
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            //定义服务端应用开发的入口
            ServerBootstrap bootstrap = new ServerBootstrap();
            //初始化主从线程池
            bootstrap.group(parentGroup, childGroup)
                    //定义服务端通道类型
                    .channel(NioServerSocketChannel.class)
                    //设置服务端处理器
                    //.childHandler(new HttpChannelInitializer())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("httpServerCodec", new HttpServerCodec());
                            pipeline.addLast("httpServerHandle", new HttpServerHandler());
                        }
                    });
            //监听666端口
            ChannelFuture future = bootstrap.bind(666).sync();
            System.out.println("服务端启动成功，开始监听666端口！");
            //关闭通道
            future.channel().closeFuture().sync();
        } finally {
            //关闭调度
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
