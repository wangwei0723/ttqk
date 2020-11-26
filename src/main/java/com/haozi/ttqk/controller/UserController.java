package com.haozi.ttqk.controller;

import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.service.UserService;
import com.haozi.ttqk.vo.ResultVo;
import com.haozi.ttqk.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/getAllUser")
    public ResultVo<UserVo> getAllUser(){
        List<UserVo> userVos=new ArrayList<>();
        List<OperationUser> operationUsers= userService.getAllUser();
        if(!CollectionUtils.isEmpty(operationUsers)){
            for (OperationUser operationUser:operationUsers) {
                UserVo userVo=new UserVo();
                BeanUtils.copyProperties(operationUser,userVo);
                userVos.add(userVo);
            }
        }

        ResultVo resultVo=new ResultVo();
        resultVo.setData(userVos);
        return resultVo;
    }

}
