package com.wdx.provider.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 * @Author: wdx
 * @Data: 2020/5/3 15:37
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Depart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
