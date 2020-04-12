package com.wdx.netty.server.demo08.sevlet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TomcatServlet {

    private Map<String, Servlet> nameToServletMap = new ConcurrentHashMap<>();

    private Map<String, String> nameToClassNameMap = new HashMap<>();

    private String basePackage;

    public TomcatServlet(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() throws Exception {
        cacheClassName(basePackage);
        runServer();
    }

    private void cacheClassName(String basePackage) {
        URL resource = this.getClass().getClassLoader()
                .getResource(basePackage.replaceAll("\\.", "/"));
        if (null == resource) {
            return;
        }
        File[] files = new File(resource.getPath()).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                cacheClassName(basePackage + "." + file);
            } else if (file.getName().endsWith(".class")) {
                String simpleClassName = file.getName().replace(".class", "").trim();
                nameToClassNameMap.put(simpleClassName.toLowerCase(),
                        basePackage + "." + simpleClassName);
            }
        }
    }

    private void runServer() throws Exception {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new TomcatHandler(nameToServletMap, nameToClassNameMap));
                        }
                    });
            ChannelFuture future = bootstrap.bind(666).sync();
            System.out.println("Tomcat成功启动，监听端口号666");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
