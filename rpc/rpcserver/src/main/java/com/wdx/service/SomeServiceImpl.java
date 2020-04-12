package com.wdx.service;

import cow.wdx.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        return name + "欢迎你!";
    }
}
