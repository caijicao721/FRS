package com.cao.frs.service;

import com.cao.frs.dao.InvoiceMapper;
import com.cao.frs.entities.Invoice;
import com.cao.frs.repos.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceMapper invoiceMapper;
    @Override
    public int add(Invoice invoice) {
        return invoiceMapper.add(invoice);
    }

    @Override
    public int remove(Integer id) {
        return invoiceMapper.remove(id);
    }

    @Override
    public int update(Map<String, Object> map) {
        return invoiceMapper.update(map);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceMapper.findAll();
    }

    @Override
    public List<Invoice> searchByName(String name) {
        return invoiceMapper.searchByName(name);
    }

    @Override
    public Invoice searchById(Integer id) {
        return invoiceMapper.searchById(id);
    }
}
