package com.wdx.kafkatest;

import java.io.IOException;

public class TwoProducerTest {

    public static void main(String[] args) throws IOException {
        TwoProducer twoProducer = new TwoProducer();
        twoProducer.sendMsg();
        System.in.read();
    }
}
