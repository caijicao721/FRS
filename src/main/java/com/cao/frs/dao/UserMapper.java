package com.cao.frs.dao;

import com.cao.frs.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserMapper {

    int add(User user);

    int remove(int id);

    int update(Map<String,Object> map);

    List<User> findAll();

}
