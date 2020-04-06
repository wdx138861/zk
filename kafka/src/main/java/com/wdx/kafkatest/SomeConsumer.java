package com.wdx.kafkatest;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class SomeConsumer extends ShutdownableThread {

    private KafkaConsumer<Integer, String> consumer;

    public SomeConsumer() {
        super("KafkaConsumerTest", false);
        Properties properties = new Properties();
        //kafka集群
        properties.put("bootstrap.servers", "121.42.13.24:9092,121.42.13.24:9093");
        //消费组id
        properties.put("group.id", "cityGroup1");
        //自动提交，默认true，会出现重复提交，解决方法手动提交：同步提交，异步提交，同异步提交
        //properties.put("enable.auto.commit", "true");

        //手动提交
        properties.put("enable.auto.commit", "false");

        //设置一次poll()读取多少条数据
        properties.put("max.poll.records", "500");
        //自动提交超时时限，默认5s
        properties.put("auto.commit.interval.ms", "1000");
        //指定消费者被broker认定为挂掉的时限。若broker在此时间内未收到当前消费者发送的心跳，则broker认定消费者已经挂掉，默认10s
        properties.put("session.timeout.ms", "30000");
        //指定两次心跳的时间间隔，默认为3s，一般不要超过session.timeout.ms的1/3
        properties.put("heartbeat.interval.ms", "10000");
        //当kafka中没有指定offset初始值时，或者指定的offset不存在时，从这里读取offset的值，
        //earliest指定为第一条，latest指定为最后一条
        properties.put("auto.offset.reset", "earliest");
        //key和value的反序列化器
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<Integer, String>(properties);
    }

    @Override
    public void doWork() {
        //订阅消息主题
        consumer.subscribe(Collections.singletonList("cities"));
        //从broker摘取消费。参数表示，若buffer中没有消息，消费者等待消费的时间
        //0：无消息什么也不返回，>0：当前时间到后仍没有消息，返回空
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord record: records) {
            System.out.println("partition = " + record.partition());
            System.out.println("topic = " + record.topic());
            System.out.println("key = " + record.key());
            System.out.println("value = " + record.value());
        }

        //手动同步提交
        //consumer.commitSync();
        //手动异步提交
        consumer.commitAsync((offset, ex) -> {
            if (ex != null) {
                System.out.println("提交失败，offset = " + offset);
                System.out.println("异常为：" + ex);
                //同步提交
                consumer.commitSync();
            }
        });
    }
}
