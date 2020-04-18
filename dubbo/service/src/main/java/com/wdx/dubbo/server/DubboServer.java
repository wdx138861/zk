package com.wdx.dubbo.server;

import com.wdx.dubbo.register.RegistryCenter;
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
import java.util.HashMap;
import java.util.Map;

public class DubboServer {

    private Map<String, Object> registerMap = new HashMap<>();

    private String serviceAddress;

    private String providerPackage;

    public void publish(RegistryCenter registryCenter, String serviceAddress,
                        String providerPackage) throws Exception {
        this.serviceAddress = serviceAddress;
        this.providerPackage = providerPackage;
        cacheClassCache(registryCenter, serviceAddress, providerPackage);
    }

    private void cacheClassCache(RegistryCenter registryCenter, String serviceAddress,
                                 String providerPackage) throws Exception {
        URL resource = this.getClass().getClassLoader()
                .getResource(providerPackage.replaceAll("\\.", "/"));
        File file = new File(resource.getPath());
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()) {
                cacheClassCache(registryCenter, serviceAddress,
                        providerPackage + "." + listFile.getName());
            } else if (listFile.getName().endsWith(".class")) {
                String fileName = listFile.getName().replace(".class", "").trim();
                Class<?> clazz = Class.forName(providerPackage + "." + fileName);
                registerMap.put(clazz.getName(), clazz.newInstance());
                registryCenter.register(clazz.getInterfaces()[0].getName(), serviceAddress);
            }
        }
    }

    public void start() throws Exception {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(parentGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new DubboServerHandler(registerMap, providerPackage));
                        }
                    });
            String[] split = serviceAddress.split(":");
            String ip = split[0];
            Integer port = Integer.parseInt(split[1]);
            ChannelFuture future = serverBootstrap.bind(ip, port).sync();
            System.out.println("服务端已启动，监听" + port + "端口");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
