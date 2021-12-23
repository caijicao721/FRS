package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.service.ReimburseSeviceImpl;
import com.cao.frs.service.UserSecurityDetailService;
import com.cao.frs.utils.out.ReimOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "报销记录")
@RestController
@RequestMapping("/reimburse")
public class ReimburseController {
    @Autowired
    ReimburseSeviceImpl reimburseseviceimpl;

    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @ApiOperation("所有报销记录")
    @PostMapping("/list")
    public List<ReimOut> getlist(){
        List<ReimOut> reimOuts = new ArrayList<>();
        List<Reimburse> list = reimburseseviceimpl.findAll();

        for (Reimburse reimburse : list) {
            reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));
        }
        //System.out.println(reimOuts);
        return reimOuts;
    }

    @ApiOperation("已完成报销记录")
    @PostMapping("/currlist")
    public List<ReimOut> getCurr(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        String user = userSecurityDetailService.findByName(username).getUsername();
        List<Reimburse> list = reimburseseviceimpl.findAll();
        List<ReimOut> reimOuts = new ArrayList<>();
        for (Reimburse reimburse : list) {
            if (userSecurityDetailService.findById(reimburse.getUserId()).getUsername().equals(user)){
                    reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));}
        }
        return reimOuts;
    }
}
