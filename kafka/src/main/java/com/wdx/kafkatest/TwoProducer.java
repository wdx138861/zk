package com.wdx.kafkatest;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class TwoProducer {
    //第一个泛型位key的类型，第二个泛型为消息体本身
    private KafkaProducer<Integer, String> producer;

    public TwoProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "121.42.13.24:9092,121.42.13.24:9093,121.42.13.24:9094");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg() {
        //指定主题、要写入的partition、key、消息本身
        ProducerRecord<Integer, String> record =
                new ProducerRecord<Integer, String>("cities", 0, 1, "shanghai");
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                System.out.println("partition = " + recordMetadata.partition());
                System.out.println("topic = " + recordMetadata.topic());
                System.out.println("offset = " + recordMetadata.offset());
            }
        });

    }
}
