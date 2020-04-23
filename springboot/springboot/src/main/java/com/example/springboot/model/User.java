package com.example.springboot.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wdx
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -869123623980426933L;

    private String name;

    private int age;

    private double score;
}
