package com.wdx.provider;

import com.wdx.service.UserService;
import java.util.concurrent.TimeUnit;

public class UserServiceImpl implements UserService {
    @Override
    public String getUserNameById(int id) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "张三";
    }

    @Override
    public void addUser(String userName) {
        System.out.println("添加用户成功");
    }
}
