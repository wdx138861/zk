package com.wdx.provider;

import com.wdx.service.SomeService;

public class NewServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        System.out.println("执行新服务：NewServiceImpl");
        return "NewServiceImpl";
    }
}
