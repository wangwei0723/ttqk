package com.haozi.ttqk.service.impl;

import com.haozi.ttqk.constant.SystemConstants;
import com.haozi.ttqk.enums.LoginEnum;
import com.haozi.ttqk.mapper.OperationLoginRecordMapper;
import com.haozi.ttqk.mapper.OperationUserMapper;
import com.haozi.ttqk.model.OperationLoginRecord;
import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private OperationUserMapper operationUserMapper;
    @Resource
    private OperationLoginRecordMapper operationLoginRecordMapper;

    public List<OperationUser> getAllUser(){
        return operationUserMapper.selectAll();
    }

    public Integer addUser(OperationUser user){
        Integer id=operationUserMapper.insertSelective(user);
        return id;
    }

    public OperationUser getUserByNameAndPwd(String name,String pwd){
        Example example=new Example(OperationUser.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("name",name);
        criteria.andEqualTo("pwd",pwd);
        criteria.andEqualTo("isDelete",0);
        List<OperationUser> operationUsers=operationUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(operationUsers)){
            return null;
        }
        return operationUsers.get(0);
    }

    public Boolean saveLoginRecord(OperationLoginRecord operationLoginRecord){
        Example example=new Example(OperationLoginRecord.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("userId",operationLoginRecord.getUserId());
        criteria.andEqualTo("isDelete",0);
        List<OperationLoginRecord> operationLoginRecords=operationLoginRecordMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(operationLoginRecords)){
            Integer num=operationLoginRecordMapper.insertSelective(operationLoginRecord);
            if(num>0){
                return true;
            }else {
                return false;
            }
        }else {
            OperationLoginRecord operationLoginRecord1=operationLoginRecords.get(0);
            operationLoginRecord.setId(operationLoginRecord1.getId());
            Integer num=operationLoginRecordMapper.updateByPrimaryKeySelective(operationLoginRecord);
            if(num>0){
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public LoginEnum checkToken(Integer userId, String token) {
        Example example=new Example(OperationLoginRecord.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("token",token);
        criteria.andEqualTo("isDelete",0);
        List<OperationLoginRecord> operationLoginRecords=operationLoginRecordMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(operationLoginRecords)){
            return LoginEnum.NOT_LOGIN;
        }
        OperationLoginRecord operationLoginRecord=operationLoginRecords.get(0);
        if(new Date().getTime()-operationLoginRecord.getLoginTime().getTime()> SystemConstants.TOKEN_VALID_TIME*60*60*1000){
            return LoginEnum.TOKEN_INVALID;
        }
        operationLoginRecord.setLoginTime(new Date());
        operationLoginRecordMapper.updateByPrimaryKeySelective(operationLoginRecord);
        return LoginEnum.LOGIN_NORMAL;
    }

}
