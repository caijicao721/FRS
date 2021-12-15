package com.cao.frs;

import com.cao.frs.dao.InvoiceMapper;
import com.cao.frs.dao.ReimburseMapper;
import com.cao.frs.dao.UserMapper;
import com.cao.frs.entities.Invoice;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class FrsApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    InvoiceMapper invoiceMapper;
    @Autowired
    ReimburseMapper reimburseMapper;
    @Test
    void contextLoads() {
        userMapper.remove(4);
        userMapper.add(new User(4,"北京",new Date(),"123@13.com",1,"教授","123","12354566","pro",200));
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("city","天津");
        map.put("isAdmin",0);
        map.put("limit",500);
        userMapper.update(map);
        List<User> all = userMapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }



    }
    @Test
    void test1(){
        invoiceMapper.add(new Invoice(6,"小绿","交通",200,1,new Date(),"122122Ad","xxxx大学"));
        List<Invoice> list1 = invoiceMapper.searchByName("小红");
        for (Invoice invoice : list1) {
            System.out.println(invoice.toString());
        }
        invoiceMapper.remove(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","小了");
        map.put("type","教育");
        map.put("hasBill",0);
        map.put("billDate",new Date());
        map.put("title","xxxxx小学");
        invoiceMapper.update(map);
        List<Invoice> all = invoiceMapper.findAll();
        for (Invoice invoice : all) {
            System.out.println(invoice);
        }

    }
    @Test
    void test2(){
        reimburseMapper.add(new Reimburse(5,2,new Date(1111111),new Date(),3,500));
        List<Reimburse> reimburses = reimburseMapper.searchByUserId(2);
        for (Reimburse reimburs : reimburses) {
            System.out.println(reimburs);
        }
        List<Reimburse> all = reimburseMapper.findAll();
        for (Reimburse reimburse : all) {
            System.out.println(reimburse);
        }
    }
}
