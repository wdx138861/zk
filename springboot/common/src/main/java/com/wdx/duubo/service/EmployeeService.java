package com.wdx.duubo.service;

import com.wdx.duubo.model.Employee;

/**
 * @Author: wdx
 * @Data: 2020/4/23 14:16
 */
public interface EmployeeService {

    /**
     * 新增员工
     * @param employee 员工
     */
    void addEmployee(Employee employee);

    /**
     * 查询员工
     * @param id 员工id
     * @return 员工
     */
    Employee selectEmployeeById(Integer id);

    /**
     * 查找员工总数
     * @return 数量
     */
    int findEmployeeCount();
}
