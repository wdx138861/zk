package com.wdx.service;

import com.wdx.service.UserService;

public class UserServiceMock implements UserService {
    @Override
    public String getUserNameById(int id) {
        return "没有该用户：" + id;
    }

    @Override
    public void addUser(String userName) {
        System.out.println("添加该用户失败： " + userName);
    }
}
