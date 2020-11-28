package com.haozi.ttqk.service;

import com.haozi.ttqk.model.OperationUser;

import java.util.List;

public interface UserService {
    List<OperationUser> getAllUser();
    Integer addUser(OperationUser user);
    Integer checkToken(Integer userId,String token);
}
