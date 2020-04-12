package com.wdx.client;

import cow.wdx.dto.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static <T> T create(Class<?> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        return rpcInvoke(clazz, method, args);
                    }
                }
        );
    }

    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) throws InterruptedException {
        RpcClientHandler rpcClientHandler = new RpcClientHandler();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(rpcClientHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 666).sync();
            System.out.println("客户端已启动，连接666端口");
            Channel channel = future.channel();
            Invocation invocation = new Invocation();
            invocation.setClassName(clazz.getName());
            invocation.setMethodName(method.getName());
            invocation.setParamTypes(method.getParameterTypes());
            invocation.setParamValues(args);
            channel.writeAndFlush(invocation).sync();

            channel.closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
        return rpcClientHandler.getResult();
    }
}
