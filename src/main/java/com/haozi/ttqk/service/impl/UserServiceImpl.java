package com.haozi.ttqk.service.impl;

import com.haozi.ttqk.mapper.OperationUserMapper;
import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private OperationUserMapper operationUserMapper;

    public List<OperationUser> getAllUser(){
        return operationUserMapper.selectAll();
    }

    public Integer addUser(OperationUser user){
        Integer id=operationUserMapper.insertSelective(user);
        return id;
    }
}
