package com.wdx.netty.server.demo04.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SomeClient {

    /**
     * 客户端粘包
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new SomeSocketClientHandler4());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 666).sync();
            System.out.println("客户端开始连接ip：127.0.0.1，端口：666的服务器");
            future.channel().closeFuture().sync();
        }finally {
            if (null != eventLoopGroup) {
                eventLoopGroup.shutdownGracefully();
            }
        }
    }
}
