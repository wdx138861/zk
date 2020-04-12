package com.wdx.consumer;

import com.wdx.client.RpcProxy;
import cow.wdx.service.SomeService;

public class RpcConsumer {

    public static void main(String[] args) {
        SomeService someService = RpcProxy.create(SomeService.class);
        System.out.println(someService.hello("wdx"));
        System.out.println(someService.hashCode());
    }
}
