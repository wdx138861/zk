package com.example.provider.dao;

import com.wdx.duubo.model.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: wdx
 * @Data: 2020/4/24 1:00
 */
@Mapper
public interface EmployeeDao {

    /**
     * 新增员工
     * @param employee 员工
     */
    void insertEmployee(Employee employee);

    /**
     * 查询员工
     * @param id 员工id
     * @return 员工信息
     */
    Employee selectEmployeeById(Integer id);

    /**
     * 查询员工总数
     * @return 数量
     */
    Integer findEmployeeCount();
}
