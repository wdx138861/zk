package com.wdx.provider;

import com.wdx.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        System.out.println(name + "，我是第三个提供者");
        return "第三个提供者! " + name;
    }
}
