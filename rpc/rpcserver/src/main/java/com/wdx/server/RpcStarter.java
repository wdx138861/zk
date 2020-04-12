package com.wdx.server;

public class RpcStarter {

    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServer();
        rpcServer.publish("com.wdx.service");
        rpcServer.start();
    }
}
