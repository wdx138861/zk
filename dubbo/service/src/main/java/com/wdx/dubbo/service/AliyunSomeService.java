package com.wdx.dubbo.service;

public class AliyunSomeService implements SomeService {
    @Override
    public String hello(String name) {
        return name + ", I am aliyun";
    }
}
