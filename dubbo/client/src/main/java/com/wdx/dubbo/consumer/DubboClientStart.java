package com.wdx.dubbo.consumer;

import com.wdx.dubbo.client.DubboClient;
import com.wdx.dubbo.service.SomeService;

public class DubboClientStart {

    public static void main(String[] args) {
        SomeService someService = DubboClient.create(SomeService.class, null);
        System.out.println(someService.hello("hi"));
    }
}
