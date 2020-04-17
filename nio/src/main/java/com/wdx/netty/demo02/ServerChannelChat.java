package com.wdx.netty.demo02;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class ServerChannelChat {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(666);
        serverChannel.bind(address);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端已启动");
        while (true) {
            if (selector.select(1000) == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isAcceptable()) {
                    SocketChannel channel = serverChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                    String msg = channel.getRemoteAddress() + "：上线了";
                    sendMsg(selector, channel, msg);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    String msg = new String(byteBuffer.array()).trim();
                    if (msg.length() > 0) {
                        SocketAddress remoteAddress = channel.getRemoteAddress();
                        if (msg.equals("886")) {
                            msg = remoteAddress + "：下线了";
                            sendMsg(selector, channel, msg);
                            key.cancel();
                        } else {
                            msg = remoteAddress + "：" + msg;
                            sendMsg(selector, channel, msg);
                        }

                    }
                }
                //todo.删除当前处理过的key，以免重复处理
                selectionKeys.remove(key);
            }
        }
    }

    //向其他客户端发送消息
    private static void sendMsg(Selector selector, SocketChannel self, String msg) throws Exception {
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel)channel;
                socketChannel.write(ByteBuffer.wrap(msg.trim().getBytes()));
            }
        }
    }
}
