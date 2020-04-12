package com.wdx.netty.server.demo08.sevlet;

public abstract class Servlet {

    public abstract void doGet(NettyRequest request, NettyResponse response) throws Exception;

    public abstract void doPost(NettyRequest request, NettyResponse response) throws Exception;
}
