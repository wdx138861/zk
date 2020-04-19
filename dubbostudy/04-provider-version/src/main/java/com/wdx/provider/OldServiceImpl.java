package com.wdx.provider;

import com.wdx.service.SomeService;

public class OldServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        System.out.println("执行旧服务：OldServiceImpl");
        return "OldServiceImpl";
    }
}
