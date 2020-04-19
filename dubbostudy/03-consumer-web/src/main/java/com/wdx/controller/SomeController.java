package com.wdx.controller;

import com.wdx.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SomeController {

    @Autowired
    private SomeService someService;

    @RequestMapping("/some.do")
    public String someHandle() {
        String result = someService.hello("china");
        System.out.println("消费者接收到：" + result);
        return "/welcome.jsp";
    }
}
