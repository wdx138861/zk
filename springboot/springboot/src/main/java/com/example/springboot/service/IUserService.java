package com.example.springboot.service;

import com.example.springboot.model.User;

/**
 * @Author: wdx
 * @Data: 2020/4/21 15:15
 */
public interface IUserService {

    /**
     * 添加用户
     * @param user 用户
     */
    void addUser(User user) throws Exception;

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return
     */
    User findUserById(int id);

    /**
     * 查询用户总人数
     * @return 数量
     */
    Integer findUserCount();
}
