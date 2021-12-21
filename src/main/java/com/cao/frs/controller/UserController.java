package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Users;
import com.cao.frs.service.UserSecurityDetailService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @ApiModelProperty("当前用户信息")
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

    @ApiModelProperty("增加用户")
    @PostMapping("/add")
    public int addUser(@RequestBody Users users){
        if (users==null){
            return 404;
        }
        System.out.println(users);
        System.out.println(users.getIsAdmin());
        int add = userSecurityDetailService.add(users);
        return add==1?200:404;
    }

    @ApiModelProperty("用户列表")
    @PostMapping("/list")
    public List<Users> getUserList(@RequestBody String keyword){
        System.out.println("keyword"+keyword);
        if (keyword.equals(" ")){
            return userSecurityDetailService.findByKeywords("");
        }else return userSecurityDetailService.findByKeywords(keyword);
    }

    @ApiModelProperty("更新用户")
    @PostMapping("/update")
    public int updateUser(@RequestBody Users users){
        int update = userSecurityDetailService.update(users);
        return update==1?200:404;
    }


    @ApiModelProperty("删除用户")
    @GetMapping("/delete")
    public int delete(int id){
        int remove = userSecurityDetailService.remove(id);
        return remove==1?200:404;
    }
}
