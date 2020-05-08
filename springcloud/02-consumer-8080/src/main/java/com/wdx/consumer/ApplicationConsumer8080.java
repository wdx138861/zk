package com.wdx.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationConsumer8080 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer8080.class, args);
    }

}
