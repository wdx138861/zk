package com.wdx.dubbo.register;

public interface RegistryCenter {

    void register(String serviceName, String serviceAddress)  throws Exception ;
}
