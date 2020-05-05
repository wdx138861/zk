package com.example.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wdx.duubo.model.Employee;
import com.wdx.duubo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wdx
 * @Data: 2020/4/24 1:29
 */
@Controller
@RequestMapping("consumer/employee")
public class EmployeeController {

    //@Autowired
    @Reference
    private EmployeeService employeeService;

    @PostMapping("register")
    public String addEmployee(Employee employee, Model model) {
        model.addAttribute("employee", employee);
        employeeService.addEmployee(employee);
        return "jsp/welcome";
    }

    @GetMapping("find")
    @ResponseBody
    public Employee addEmployee(Integer id) {
        return  employeeService.selectEmployeeById(id);
    }

    @GetMapping("count")
    @ResponseBody
    public Integer findEmployeeCount() {
        return  employeeService.findEmployeeCount();
    }
}
