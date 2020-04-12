package com.wdx.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpcServer {

    //注册表：key为接口名称，value为实现类名称
    private Map<String, Object> registerMap = new HashMap<>();

    //业务接口是实现类集合
    private List<String> classCacheList = new ArrayList<>();

    //发布服务
    public void publish(String basePackage) throws Exception {
        cacheClassCache(basePackage);
        doRegister();
    }

    //写入类名到集合
    private void cacheClassCache(String basePackage) {
        URL resource = this.getClass().getClassLoader()
                .getResource(basePackage.replaceAll("\\.", "/"));
        if (null == resource) {
            return;
        }
        File[] files = new File(resource.getFile()).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                cacheClassCache(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")){
                String fileName = file.getName().replaceAll(".class", "").trim();
                classCacheList.add(basePackage + "." + fileName);
            }
        }
    }

    //将接口类写入注册表
    private void doRegister() throws Exception {
        if (null == classCacheList) {
            return;
        }
        for (String className : classCacheList) {
            Class<?> clazz = Class.forName(className);
            registerMap.put(clazz.getInterfaces()[0].getName(), clazz.newInstance());
        }
    }

    //启动服务类
    public void start() throws InterruptedException {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    //server请求全被占用时，临时存放完成三次握手的请求队列长度，默认50
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //指定使用心跳机制来保证TCP长连接的存活性
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new RpcServerHandler(registerMap));
                        }
                    });
            ChannelFuture future = bootstrap.bind(666).sync();
            System.out.println("服务端已启动，开始监听666端口");
            future.channel().closeFuture().sync();
        }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
