package com.wdx.kafkatest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class BatchProducer {
    //第一个泛型位key的类型，第二个泛型为消息体本身
    private KafkaProducer<Integer, String> producer;

    public BatchProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "121.42.13.24:9092,121.42.13.24:9093");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("batch.size", 10);
        properties.put("linger.ms", 50);
        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg() {
        //指定主题、要写入的partition、key、消息本身
        for (int i = 0; i < 50; i++) {
            ProducerRecord<Integer, String> record =
                    new ProducerRecord<Integer, String>("cities", 0, i * 10, "city-" + i);
            int k = i;
            producer.send(record, (recordMetadata, exception) -> {
                System.out.println("k = " + k);
                System.out.println("partition = " + recordMetadata.partition());
                System.out.println("topic = " + recordMetadata.topic());
                System.out.println("offset = " + recordMetadata.offset());
            });
        }

    }
}
