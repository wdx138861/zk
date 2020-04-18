package com.wdx.dubbo.server;

import com.wdx.dubbo.dto.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DubboServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> registerMap = new HashMap<>();

    private String providerPackage;

    public DubboServerHandler(Map<String, Object> registerMap, String providerPackage) {
        this.registerMap = registerMap;
        this.providerPackage = providerPackage;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = "没有此对象";
        if (msg instanceof Invocation) {
            Invocation invocation = (Invocation) msg;
            String interfaceName = invocation.getClazzName();
            String simpleInterfaceName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1);
            String clazzName = providerPackage + "." + invocation.getPrefix() + simpleInterfaceName;
            if (StringUtil.isNullOrEmpty(invocation.getPrefix())) {
                for (String key : registerMap.keySet()) {
                    if (key.endsWith(simpleInterfaceName)) {
                        clazzName = key;
                        break;
                    }
                }
            }
            if (registerMap.containsKey(clazzName)) {
                Object obj = registerMap.get(clazzName);
                Method method = obj.getClass().getMethod(invocation.getMethodName(), invocation.getParameterList());
                result = method.invoke(obj, invocation.getValues());
            }

        }
        ctx.writeAndFlush(result + " 信息来自：" + ctx.channel().remoteAddress());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
