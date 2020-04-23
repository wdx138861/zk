package com.example.springboot.dao;

import com.example.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: wdx
 * @Data: 2020/4/21 15:25
 */
@Mapper
public interface IUserDao {

    /**
     * 新增用户
     * @param user 用户
     */
    void insertUser(User user);

    /**
     * 查询用户
     * @param id 用户id
     * @return 用户
     */
    User selectUserById(Integer id);

    /**
     * 查询用户数量
     * @return 个数
     */
    Integer selectUserCount();
}
