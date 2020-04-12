package com.wdx.netty.server.demo08.sevlet;

public interface NettyResponse {

    void write(String content) throws Exception;
}
