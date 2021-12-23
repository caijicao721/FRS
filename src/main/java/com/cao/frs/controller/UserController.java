package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Users;
import com.cao.frs.service.UserSecurityDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @ApiOperation("当前用户信息")
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

    @ApiOperation("增加用户")
    @PostMapping("/add")
    public int addUser(@RequestBody Users users){
        users.setId(0);
        System.out.println(users);
        System.out.println(users.getIsAdmin());
        int add = userSecurityDetailService.add(users);
        return add==1?200:404;
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public List<Users> getUserList(@RequestBody String keyword){
        System.out.println("keyword"+keyword);
        if (keyword.equals(" ")){
            return userSecurityDetailService.findByKeywords("");
        }else return userSecurityDetailService.findByKeywords(keyword);
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public int updateUser(@RequestBody Users users){
        int update = userSecurityDetailService.update(users);
        return update==1?200:404;
    }


    @ApiOperation("删除用户")
    @GetMapping("/delete")
    public int delete(int id){
        int remove = userSecurityDetailService.remove(id);
        return remove==1?200:404;
    }
}
