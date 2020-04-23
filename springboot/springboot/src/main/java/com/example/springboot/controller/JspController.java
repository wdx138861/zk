package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wdx
 */
@Controller
@RequestMapping(value = "jsp")
public class JspController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "welcome")
    public String welcome(User user, Model model) {
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "jsp/welcome";
    }

    @RequestMapping(value = "find")
    @ResponseBody
    public User find(int id) {
        return userService.findUserById(id);
    }

    @RequestMapping(value = "userCount")
    @ResponseBody
    public Integer userCount() {
        return userService.findUserCount();
    }

}
