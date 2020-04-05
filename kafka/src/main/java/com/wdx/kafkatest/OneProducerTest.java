package com.wdx.kafkatest;

import java.io.IOException;

public class OneProducerTest {

    public static void main(String[] args) throws IOException {
        OneProducer oneProducer = new OneProducer();
        oneProducer.sendMsg();
        System.in.read();
    }
}
