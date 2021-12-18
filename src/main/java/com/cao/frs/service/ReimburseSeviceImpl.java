package com.cao.frs.service;

import com.cao.frs.dao.ReimburseMapper;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.repos.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReimburseSeviceImpl implements ReimburseService {
    @Autowired
    private ReimburseMapper reimburseMapper;
    @Override
    public int add(Reimburse reimburse) {
        return reimburseMapper.add(reimburse);
    }

    @Override
    public List<Reimburse> findAll() {
        return reimburseMapper.findAll();
    }

    @Override
    public List<Reimburse> searchByUserId(Integer userId) {
        return reimburseMapper.searchByUserId(userId);
    }
}
