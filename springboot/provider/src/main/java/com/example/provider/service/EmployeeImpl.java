package com.example.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.provider.dao.EmployeeDao;
import com.wdx.duubo.model.Employee;
import com.wdx.duubo.service.EmployeeService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wdx
 * @Data: 2020/4/24 0:58
 */
@Service
@Component
public class EmployeeImpl implements EmployeeService {

    @Autowired
    private EmployeeDao dao;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @CacheEvict(value = "realTimeCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEmployee(Employee employee) {
        dao.insertEmployee(employee);
    }

    @Cacheable(value = "realTimeCache", key = "'employee' + #id")
    @Override
    public Employee selectEmployeeById(Integer id) {
        return dao.selectEmployeeById(id);
    }

    @Override
    public int findEmployeeCount() {
        BoundValueOperations<Object, Object> count = redisTemplate.boundValueOps("count");
        Object object = count.get();
        if (null == object) {
            synchronized (this) {
                object = count.get();
                if (null == object) {
                    object = dao.findEmployeeCount();
                    count.set(object, 10, TimeUnit.SECONDS);
                }
            }
        }
        return (int)object;
    }
}
