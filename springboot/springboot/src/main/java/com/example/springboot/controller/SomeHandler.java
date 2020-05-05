package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wdx
 * @Data: 2020/4/27 22:59
 */
@Controller
public class SomeHandler {

    @RequestMapping("first/some")
    @ResponseBody
    public String someHandler() {
        return "first/some";
    }

    @RequestMapping("second/other")
    @ResponseBody
    public String otherHandler() {
        return "second/other";
    }
}
