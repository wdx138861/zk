package com.wdx.provider;

import com.wdx.service.SomeService;

public class WexinServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        System.out.println("执行微信服务：WexinServiceImpl");
        return "WexinServiceImpl";
    }
}
