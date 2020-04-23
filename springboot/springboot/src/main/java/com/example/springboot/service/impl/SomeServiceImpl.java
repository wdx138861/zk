package com.example.springboot.service.impl;

import com.example.springboot.service.SomeService;
import org.springframework.stereotype.Service;

/**
 * @author wdx
 */
@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("SomeServiceImpl的doSome()方法");
    }
}
