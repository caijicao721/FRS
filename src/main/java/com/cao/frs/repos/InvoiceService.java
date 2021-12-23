package com.cao.frs.repos;

import com.cao.frs.entities.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceService {
    int add(Invoice invoice);

    int remove(Integer id);

    int update(Map<String,Object> map);

    List<Invoice> findAll();

    List<Invoice> searchByName(String name);

    Invoice searchById(Integer id);
}
