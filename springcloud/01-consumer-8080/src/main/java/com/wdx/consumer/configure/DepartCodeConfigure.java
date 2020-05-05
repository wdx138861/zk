package com.wdx.consumer.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: wdx
 * @Data: 2020/5/3 17:13
 */
@Controller
public class DepartCodeConfigure {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
