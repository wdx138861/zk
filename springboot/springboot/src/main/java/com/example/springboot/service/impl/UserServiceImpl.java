package com.example.springboot.service.impl;

import com.example.springboot.dao.IUserDao;
import com.example.springboot.model.Student;
import com.example.springboot.model.User;
import com.example.springboot.service.IUserService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wdx
 * @Data: 2020/4/21 15:24
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @CacheEvict(value = "realTimeCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(User user) throws Exception {
        userDao.insertUser(user);
    }


    /*@CacheEvict(value = "realTimeCache", key = "'user_' + #id")*/
    @Override
    public User findUserById(int id) {
        //获取redis操作对象
        String key = "user_" + id;
        //设置序列化，防止默认序列化key乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(key);
        //从缓存中读取数据
        Object object = ops.get();
        if (object == null) {
            synchronized (this) {
                object = ops.get();
                if (object == null) {
                    object= userDao.selectUserById(id);
                    if (object != null) {
                        ops.set(object);
                    }
                }
            }
        }
        return (User)object;
    }

    /**
     * 使用双重检测锁解决热点缓存问题
     * @return
     */
    @Override
    public Integer findUserCount() {
        //获取redis操作对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps("count");
        //从缓存中读取数据
        Object count = ops.get();
        if (count == null) {
            synchronized (this) {
                count = ops.get();
                if (count == null) {
                    //从数据库中查询
                    count = userDao.selectUserCount();
                    //将数据写入缓存，并设置到期时间
                    ops.set(count, 10, TimeUnit.SECONDS);
                }
            }
        }
        return (Integer)count;
    }
}
