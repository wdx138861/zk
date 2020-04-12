package com.wdx.netty.server.demo08.sevlet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TomcatHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Servlet> nameToServletMap = new ConcurrentHashMap<>();

    private Map<String, String> nameToClassNameMap = new HashMap<>();

    public TomcatHandler(Map<String, Servlet> nameToServletMap, Map<String, String> nameToClassNameMap) {
        this.nameToServletMap = nameToServletMap;
        this.nameToClassNameMap = nameToClassNameMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String servletName = request.getUri().split("/")[1];
            Servlet servlet = new DefaultServlet();
            if (nameToServletMap.containsKey(servletName)) {
                servlet = nameToServletMap.get(servletName);
            } else if (nameToClassNameMap.containsKey(servletName)) {
                if (nameToServletMap.get(servletName) == null) {
                    synchronized (this) {
                        if (nameToServletMap.get(servletName) == null) {
                            String className = nameToClassNameMap.get(servletName);
                            servlet = (Servlet) Class.forName(className).newInstance();
                            nameToServletMap.put(servletName, servlet);
                        }
                    }
                }
            }
            NettyRequest req = new DefaultNettyRequest(request);
            DefaultNettyResponse resp = new DefaultNettyResponse(request, ctx);
            if (request.getMethod().name().equalsIgnoreCase("GET")) {
                servlet.doGet(req, resp);
            } else if (request.getMethod().name().equalsIgnoreCase("POST")) {
                servlet.doPost(req, resp);
            }
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
