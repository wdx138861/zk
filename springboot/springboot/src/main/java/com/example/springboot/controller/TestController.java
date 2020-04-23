package com.example.springboot.controller;

import com.example.springboot.model.Group;
import com.example.springboot.model.Student;
import com.example.springboot.service.ISomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wdx
 */
@RestController
@RequestMapping(value = "test")
@PropertySource(value = "classpath:myapp.properties", encoding = "utf-8")
public class TestController {

    @Value("${student.name}")
    private String name;

    @Autowired
    private ISomeService someService;

    @Autowired
    private Student student;

    @Autowired
    private Group group;

    @RequestMapping(value = "hello")
    public Object hello() {
        return "hello";
    }

    @RequestMapping(value = "error500")
    public Object error500() {
        int i = 3 / 0;
        return "hello";
    }

    @RequestMapping(value = "some")
    public Object someHandle() {
        return someService.send();
    }

    @RequestMapping(value = "name")
    public Object getName() {
        return name;
    }

    @RequestMapping(value = "studentName")
    public Object getStudentName() {
        System.out.println("student =" + student);
        return student.getName();
    }

    @RequestMapping(value = "user")
    public Object user() {
        return group;
    }
}
