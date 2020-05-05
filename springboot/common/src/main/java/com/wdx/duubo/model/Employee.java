package com.wdx.duubo.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @Author: wdx
 * @Data: 2020/4/23 13:40
 */
@Data
public class Employee implements Serializable {

    private Integer id;

    private String name;

    private int age;

}
