package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cao.frs.entities.Invoice;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.service.InvoiceServiceImpl;
import com.cao.frs.service.ReimburseSeviceImpl;
import com.cao.frs.service.UserSecurityDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Api(tags = "报销申请")
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    InvoiceServiceImpl invoiceService;

    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @Autowired
    ReimburseSeviceImpl reimburseSevice;


    @ApiOperation("添加一条申请")
    @PostMapping("/add")
    public int addInvoice(@RequestBody Invoice invoice){
        int add = invoiceService.add(invoice);
        return add==1?200:404;
    }

    @ApiOperation("当前用户申请记录")
    @PostMapping("/currlist")
    public List<Invoice> getCurrList(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        Users users = userSecurityDetailService.findByName(username);
        return invoiceService.searchByName(users.getNickname());
    }


    @ApiOperation("删除申请")
    @GetMapping("/delete")
    public int removeInvoice(Integer id) {
        int code = invoiceService.remove(id);
        return code==1?200:404;
    }


    @ApiOperation("更改报销申请")
    @PostMapping("/update")
    public int updateInvoice(@RequestBody Invoice invoice){
        int update = invoiceService.update(BeanUtil.beanToMap(invoice));
        return update==1?200:404;
    }


    @ApiOperation("当前编辑记录id")
    @GetMapping("/detail")
    public Invoice getCurrInvoice(Integer id){
        return invoiceService.searchById(id);
    }

    @ApiOperation("所有申请列表")
    @PostMapping("list")
    public List<Invoice> getlist(){
        return invoiceService.findAll();
    }


    @ApiOperation("管理员审批同意")
    @GetMapping("/approve")
    public int approve(Integer id){
        Invoice invoice = invoiceService.searchById(id);
        System.out.println(invoice.getName());
        Users users = userSecurityDetailService.findByNickName(invoice.getName());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        Users operate = userSecurityDetailService.findByName(username);
        Reimburse reimburse = new Reimburse();
        reimburse.setUserId(users.getId());
        reimburse.setOperateId(operate.getId());
        reimburse.setCreateTime(invoice.getBillDate());
        reimburse.setEndTime(new Date());
        reimburse.setMoney(invoice.getMoney());
        int add = reimburseSevice.add(reimburse);
        return add==1?200:404;
    }
}
