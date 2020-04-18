package com.wdx.dubbo.client;

import com.wdx.dubbo.dto.Invocation;
import com.wdx.dubbo.server.ServiceDiscovery;
import com.wdx.dubbo.server.ZKServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DubboClient {

    public static <T> T create(Class<?> clazz, String prefix) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        return client(clazz, method, args, prefix);
                    }
                });
    }

    private static Object client(Class<?> clazz, Method method, Object[] args, String prefix) throws Exception {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        DubboClientHandler clientHandler = new DubboClientHandler();
        try {
            bootstrap.group(loopGroup)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(clientHandler);
                        }
                    });

            ServiceDiscovery discovery = new ZKServiceDiscovery();
            String serverAddress = discovery.discovery(clazz.getName());
            if (serverAddress == null) {
                return null;
            }
            String ip = serverAddress.split(":")[0];
            Integer port = Integer.parseInt(serverAddress.split(":")[1]);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            System.out.println("客户端已连接ip：" + ip + "，端口：" + port);
            Invocation invocation = new Invocation();
            invocation.setClazzName(clazz.getName());
            invocation.setMethodName(method.getName());
            invocation.setParameterList(method.getParameterTypes());
            invocation.setValues(args);
            invocation.setPrefix(prefix);
            future.channel().writeAndFlush(invocation).sync();
            future.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
        return clientHandler.getResult();
    }
}
