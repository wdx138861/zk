package com.wdx.kafkatest;

import java.io.IOException;

public class TwoProducerTest {

    public static void main(String[] args) throws IOException {
        TwoProducer oneProducer = new TwoProducer();
        oneProducer.sendMsg();
        System.in.read();
    }
}
