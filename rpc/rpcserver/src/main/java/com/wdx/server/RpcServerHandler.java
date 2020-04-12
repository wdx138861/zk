package com.wdx.server;

import cow.wdx.dto.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<Invocation> {

    private Map<String, Object> registerMap = new HashMap<>();

    public RpcServerHandler(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) throws Exception {
        Object result = "没有提供者，或没有该方法";
        if (registerMap.containsKey(invocation.getClassName())) {
            Object invoker = registerMap.get(invocation.getClassName());
            Method method = invoker.getClass().getMethod(invocation.getMethodName(), invocation.getParamTypes());
            result = method.invoke(invoker, invocation.getParamValues());
        }
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
