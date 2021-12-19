package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Users;
import com.cao.frs.service.UserSecurityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserSecurityDetailService userSecurityDetailService;


    @GetMapping("/currUser")
    public Users getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal!=null){
            Map<String,Object> map = BeanUtil.beanToMap(principal);
            String username =(String) map.get("username");
            if (StrUtil.isNotBlank(username)){
                return userSecurityDetailService.findByName(username);
            }
        }
        return null;
    }
}
