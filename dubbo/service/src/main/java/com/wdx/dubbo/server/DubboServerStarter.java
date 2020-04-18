package com.wdx.dubbo.server;

import com.wdx.dubbo.register.ZKRegistryCenter;

public class DubboServerStarter {

    public static void main(String[] args) throws Exception {
        DubboServer dubboServer = new DubboServer();
        ZKRegistryCenter zkRegistryCenter = new ZKRegistryCenter();
        String serviceAddress = "127.0.0.1:666";
        String providerService = "com.wdx.dubbo.service";
        dubboServer.publish(zkRegistryCenter, serviceAddress, providerService);
        dubboServer.start();
    }
}
