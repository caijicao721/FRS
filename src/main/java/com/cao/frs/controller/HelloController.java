package com.cao.frs.controller;

import com.cao.frs.entities.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/user")
    public User user(){
        return new User();
    }
}
