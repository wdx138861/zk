package com.wdx.springbootkafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Value("${kafka.topic}")
    private String topic;

    @GetMapping(value = "msg/send")
    public String sendMsg(@RequestParam String message) {
        template.send(topic, message);
        return "send success";
    }

}
