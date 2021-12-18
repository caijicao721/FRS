package com.cao.frs.repos;

import com.cao.frs.entities.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    int add(Users users);

    int remove(int id);

    int update(Map<String,Object> map);

    List<Users> findAll();
}
