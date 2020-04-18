package com.wdx.dubbo.service;

public class WechatSomeService implements SomeService {
    @Override
    public String hello(String name) {
        return name + ", I am wechat";
    }
}
