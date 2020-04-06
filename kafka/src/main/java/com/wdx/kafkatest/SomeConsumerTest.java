package com.wdx.kafkatest;

import java.io.IOException;

public class SomeConsumerTest {

    public static void main(String[] args) throws IOException {
        SomeConsumer someConsumer = new SomeConsumer();
        someConsumer.start();
    }
}
