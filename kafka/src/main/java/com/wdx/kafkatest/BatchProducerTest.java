package com.wdx.kafkatest;

import java.io.IOException;

public class BatchProducerTest {

    public static void main(String[] args) throws IOException {
        BatchProducer batchProducer = new BatchProducer();
        batchProducer.sendMsg();
        System.in.read();
    }
}
