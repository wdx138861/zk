package com.wdx.netty.demo01;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerChannel {

    public static void main(String[] args) throws Exception {
        //创建服务端channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //指定channel为非阻塞的
        serverChannel.configureBlocking(false);
        //指定服务端端口号
        SocketAddress socketAddress = new InetSocketAddress(666);
        //服务端绑定端口
        serverChannel.bind(socketAddress);
        //创建一个多路复用selector选择器
        Selector selector = Selector.open();
        //将服务端channel注册selector，让selector监听"接收channel连接事件"
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //select()是一个阻塞方法，若阻塞1s时间到了或者阻塞期间内有channel就绪，就会打破阻塞
            if (selector.select(1000) == 0) {
                System.out.println("暂时没有就绪的channel");
                continue;
            }

            //已经有就绪channel
            //获取所有就绪的channel的key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            /*selectionKeys.forEach(key -> {
                //若当前key为OP_READ，则说明当前的channel是可连接的
                if (key.isAcceptable()) {
                    try {
                        System.out.println("接收到客户端的连接");
                        //获取连接到服务端channel上的客户端（代理客户端）
                        SocketChannel accept = serverChannel.accept();
                        //客户端channel非阻塞
                        accept.configureBlocking(false);
                        //将客户端放入selector，并让selector监听这个channel是否发生读事件
                        accept.register(key.selector(), SelectionKey.OP_READ);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //若当前的key为OP_READ，则当前channel为客户端发送来的数据
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                    try {
                        channel.read(byteBuffer);
                        System.out.println("客户端信息：" + new String(byteBuffer.array()));
                    } catch (Exception e) {
                        channel.close();
                    }
                }
                //删除当前处理过的key，以免重复处理
                selectionKeys.remove(key);
            });*/
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey it = iterator.next();
                //若当前key为OP_READ，则说明当前的channel是可连接的
                if (it.isAcceptable()) {
                    try {
                        System.out.println("接收到客户端的连接");
                        //获取连接到服务端channel上的客户端（代理客户端）
                        SocketChannel accept = serverChannel.accept();
                        //客户端channel非阻塞
                        accept.configureBlocking(false);
                        //将客户端放入selector，并让selector监听这个channel是否发生读事件
                        accept.register(it.selector(), SelectionKey.OP_READ);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //若当前的key为OP_READ，则当前channel为客户端发送来的数据
                if (it.isReadable()) {
                    SocketChannel channel = (SocketChannel)it.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                    try {
                        channel.read(byteBuffer);
                        System.out.println("客户端信息：" + new String(byteBuffer.array()));
                    } catch (Exception e) {
                        channel.close();
                    }
                }
                //删除当前处理过的key，以免重复处理
                iterator.remove();
            }
        }
    }
}
