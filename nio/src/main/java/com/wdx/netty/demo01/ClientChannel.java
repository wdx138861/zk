package com.wdx.netty.demo01;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientChannel {

    public static void main(String[] args) throws Exception {
        //创建一个客户端channel
        SocketChannel clientChannel = SocketChannel.open();
        //指定channel为非阻塞的
        clientChannel.configureBlocking(false);
        //指定服务端ip和端口号
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 666);
        //channel第一次连接服务端
        if (!clientChannel.connect(socketAddress)) {
            //由于channel连接时异步的，需要不断重连
            while(!clientChannel.finishConnect()) {
                System.out.println("进行重连中");
                continue;
            }
        }
        clientChannel.write(ByteBuffer.wrap("Hello Server".getBytes()));
        System.out.println("客户端已发送数据");
        System.in.read();
    }
}
