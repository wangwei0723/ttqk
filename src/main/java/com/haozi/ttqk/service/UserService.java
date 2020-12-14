package com.haozi.ttqk.service;

import com.haozi.ttqk.enums.LoginEnum;
import com.haozi.ttqk.model.OperationLoginRecord;
import com.haozi.ttqk.model.OperationUser;

import java.util.List;

public interface UserService {
    List<OperationUser> getAllUser();
    Integer addUser(OperationUser user);
    OperationUser getUserByNameAndPwd(String name,String pwd);
    Boolean saveLoginRecord(OperationLoginRecord operationLoginRecord);
    LoginEnum checkToken(Integer userId, String token);
}
