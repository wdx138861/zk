package com.wdx.provider;

import com.wdx.service.SomeService;

public class ZhifubaoServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        System.out.println("执行支付宝服务：ZhifubaoServiceImpl");
        return "ZhifubaoServiceImpl";
    }
}
