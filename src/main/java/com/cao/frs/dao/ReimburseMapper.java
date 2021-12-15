package com.cao.frs.dao;

import com.cao.frs.entities.Reimburse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReimburseMapper {

    int add(Reimburse reimburse);

    List<Reimburse> findAll();

    List<Reimburse>  searchByUserId(Integer userId);
}
