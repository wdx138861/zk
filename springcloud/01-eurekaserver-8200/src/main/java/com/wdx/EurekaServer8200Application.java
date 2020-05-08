package com.wdx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka sever
 * @author wdx
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer8200Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer8200Application.class, args);
    }

}
