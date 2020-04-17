package com.wdx.netty.demo02;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientChannelChat {

    public static void main(String[] args) throws Exception {
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("localhost", 666);
        if (!clientChannel.connect(address)) {
            while (!clientChannel.finishConnect()) {
                continue;
            }
        }
        SocketAddress localAddress = clientChannel.getLocalAddress();
        System.out.println(localAddress + "你已成功上线了");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!clientChannel.isConnected()) {
                        return;
                    }
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        clientChannel.read(byteBuffer);
                        String msg = new String(byteBuffer.array()).trim();
                        if (msg.length() > 0) {
                            System.out.println(msg);
                        }
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            clientChannel.write(ByteBuffer.wrap(nextLine.getBytes()));
            if (nextLine.equals("886")) {
                clientChannel.close();
                return;
            }
        }
    }
}
