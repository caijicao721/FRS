package com.cao.frs;

import com.cao.frs.entities.Invoice;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.repos.InvoiceService;
import com.cao.frs.repos.ReimburseService;
import com.cao.frs.repos.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class FrsApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ReimburseService reimburseService;
    @Test
    void contextLoads() {
        userService.remove(4);
        userService.add(new Users(4,"北京",new Date(),"123@13.com",1,"教授","123","12354566","pro",200));
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("city","天津");
        map.put("isAdmin",0);
        map.put("limit",500);
        userService.update(map);
        List<Users> all = userService.findAll();
        for (Users users : all) {
            System.out.println(users);
        }



    }
    @Test
    void test1(){
        invoiceService.add(new Invoice(6,"小绿","交通",200,1,new Date(),"122122Ad","xxxx大学"));
        List<Invoice> list1 = invoiceService.searchByName("小红");
        for (Invoice invoice : list1) {
            System.out.println(invoice.toString());
        }
        invoiceService.remove(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","小了");
        map.put("type","教育");
        map.put("hasBill",0);
        map.put("billDate",new Date());
        map.put("title","xxxx小学");
        invoiceService.update(map);
        List<Invoice> all = invoiceService.findAll();
        for (Invoice invoice : all) {
            System.out.println(invoice);
        }

    }
    @Test
    void test2(){
        reimburseService.add(new Reimburse(5,2,new Date(1111111),new Date(),3,500));
        List<Reimburse> reimburses = reimburseService.searchByUserId(2);
        for (Reimburse reimburs : reimburses) {
            System.out.println(reimburs);
        }
        List<Reimburse> all = reimburseService.findAll();
        for (Reimburse reimburse : all) {
            System.out.println(reimburse);
        }
    }
}
