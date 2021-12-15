package com.cao.frs.dao;

import com.cao.frs.entities.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface InvoiceMapper {


    int add(Invoice invoice);

    int remove(Integer id);

    int update(Map<String,Object> map);

    List<Invoice> findAll();

    List<Invoice> searchByName(String name);
}
