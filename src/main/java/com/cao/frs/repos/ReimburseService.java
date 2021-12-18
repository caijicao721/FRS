package com.cao.frs.repos;

import com.cao.frs.entities.Reimburse;

import java.util.List;

public interface ReimburseService {

    int add(Reimburse reimburse);

    List<Reimburse> findAll();

    List<Reimburse>  searchByUserId(Integer userId);
}
