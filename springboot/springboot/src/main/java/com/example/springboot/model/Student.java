package com.example.springboot.model;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author wdx
 */
@Component
@PropertySource(value = "classpath:myapp.properties", encoding = "utf-8")
@ConfigurationProperties(value = "student")
@Data
public class Student {

    private String name;

    private int age;

    private double score;

    private List<String> cities;
}
