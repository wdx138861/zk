package com.wdx.dubbo.server;

public interface ServiceDiscovery {

    String discovery(String serviceName) throws Exception;
}
