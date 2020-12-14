package com.haozi.ttqk.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.haozi.ttqk.model.OperationLoginRecord;
import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.model.OperationUserFeatures;
import com.haozi.ttqk.model.TtDic;
import com.haozi.ttqk.service.MenuService;
import com.haozi.ttqk.service.UserService;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@RequestMapping("/user")
@RestController
@Api(value = "账号管理", description = "账号管理", protocols = "http")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @PostMapping("/login")
    public ResultVo<Map<String,Object>> login(AddUserVo addUserVo){
        Map<String,Object> map=new HashMap<>();
        String token=null;
        try {
            if(addUserVo==null){
                return ResponseUtil.fail("参数不能为空");
            }
            if(StringUtils.isEmpty(addUserVo.getName())){
                return ResponseUtil.fail("用户名不能为空");
            }
            if(StringUtils.isEmpty(addUserVo.getPwd())){
                return ResponseUtil.fail("密码不能为空");
            }
            OperationUser operationUser=userService.getUserByNameAndPwd(addUserVo.getName(),addUserVo.getPwd());
            if(operationUser==null){
                log.info("用户名或密码错误,用户名[{}],密码[{}]",addUserVo.getName(),addUserVo.getPwd());
                return ResponseUtil.fail("用户名或密码错误");
            }
            token= UUID.randomUUID().toString();
            OperationLoginRecord operationLoginRecord=new OperationLoginRecord();
            operationLoginRecord.setUserId(operationUser.getId());
            operationLoginRecord.setToken(token);
            operationLoginRecord.setLoginTime(new Date());
            Boolean flag=userService.saveLoginRecord(operationLoginRecord);
            if(!flag){
                log.info("保存登录记录失败,用户名[{}],密码[{}]",addUserVo.getName(),addUserVo.getPwd());
                return ResponseUtil.fail("登录失败");
            }
            map.put("token",token);
            map.put("userId",1);
        } catch (Exception e) {
            log.info("添加用户出现异常",e);
            ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(map);
    }

    @ApiOperation(value = "获取所有用户", httpMethod = "POST")
    @PostMapping("/getAllUser")
    public ResultVo<UserVo> getAllUser(){
        List<UserVo> userVos=new ArrayList<>();
        try {
            List<OperationUser> operationUsers= userService.getAllUser();
            if(!CollectionUtils.isEmpty(operationUsers)){
                for (OperationUser operationUser:operationUsers) {
                    UserVo userVo=new UserVo();
                    BeanUtils.copyProperties(operationUser,userVo);
                    userVos.add(userVo);
                }
            }
        } catch (Exception e) {
            log.info("获取所有用户出现异常",e);
            ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(userVos);
    }

    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @PostMapping("/addUser")
    public ResultVo<Integer> addUser(AddUserVo addUserVo){
        Integer id=null;
        try {
            if(addUserVo==null){
                return ResponseUtil.fail("参数不能为空");
            }
            if(StringUtils.isEmpty(addUserVo.getName())){
                return ResponseUtil.fail("用户名不能为空");
            }
            if(StringUtils.isEmpty(addUserVo.getPwd())){
                return ResponseUtil.fail("密码不能为空");
            }
            OperationUser operationUser=new OperationUser();
            BeanUtils.copyProperties(addUserVo,operationUser);
            id=userService.addUser(operationUser);
        } catch (Exception e) {
            log.info("添加用户出现异常",e);
            ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(id);
    }
    @ApiOperation(value = "保存用户权限", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "authorityList", value = "权限列表(权限ID列表字符串)", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/saveAuthority")
    public ResultVo<Boolean> saveAuthority(Integer userId,String authorityList){
        try {
            if(userId==null){
                ResponseUtil.fail("用户ID能为空");
            }

            List<OperationUserFeatures> userFeatures=new ArrayList<>();
            if(!StringUtils.isEmpty(authorityList)){
                List<Integer> featureIds= JSONObject.parseObject(authorityList,new TypeReference<List<Integer>>(){});
                if(!CollectionUtils.isEmpty(featureIds)){
                    for (Integer featureId:featureIds) {
                        OperationUserFeatures operationUserFeatures=new OperationUserFeatures();
                        operationUserFeatures.setUserId(userId);
                        operationUserFeatures.setFeaturesId(featureId);
                        operationUserFeatures.setIsDelete(0);
                        userFeatures.add(operationUserFeatures);
                    }
                }
            }
            Boolean flag=menuService.saveUserFeature(userFeatures,userId);
            if(!flag){
                ResponseUtil.fail("保存权限失败");
            }
        } catch (Exception e) {
            log.info("保存用户权限出现异常",e);
            ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(true);
    }
    @ApiOperation(value = "获取所有菜单", httpMethod = "POST")
    @PostMapping("/getAllMenuList")
    public ResultVo<UserMenuVo> getAllMenuList(){
        List<UserMenuVo> menuVos=new ArrayList<>();
        try {
            List<Integer> typeIds=new ArrayList<>();
            typeIds.add(3);
            typeIds.add(4);
            List<TtDic> ttDicList=menuService.getMenuListByTypes(typeIds);
            if(!CollectionUtils.isEmpty(ttDicList)){
                List<TtDic> parentMenu=ttDicList.stream().filter(x->x.getParentId()==null).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(parentMenu)){
                    for (TtDic ttDic:parentMenu) {
                        UserMenuVo userMenuVo=new UserMenuVo();
                        BeanUtils.copyProperties(ttDic,userMenuVo);
                        List<TtDic> childrenMenu=ttDicList.stream().filter(x->(x.getParentId()!=null && x.getParentId().equals(ttDic.getId()))).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(childrenMenu)){
                            List<ChildMenuVo> childMenuVos=new ArrayList<>();
                            for (TtDic ttDic1:childrenMenu) {
                                ChildMenuVo childMenuVo=new ChildMenuVo();
                                BeanUtils.copyProperties(ttDic1,childMenuVo);
                                childMenuVos.add(childMenuVo);
                            }
                            userMenuVo.setChildrenMenuList(childMenuVos);
                        }
                        menuVos.add(userMenuVo);
                    }
                }
            }
        } catch (Exception e) {
            log.info("查询所有菜单出现异常",e);
            return ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(menuVos);
    }

    @ApiOperation(value = "获取用户菜单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getUserMenuList")
    public ResultVo<UserMenuVo> getUserMenuList(Integer userId){
        List<UserMenuVo> menuVos=new ArrayList<>();
        try {
            List<OperationUserFeatures> userFeatures=menuService.getUserFeaturesByUserId(userId);
            if(!CollectionUtils.isEmpty(userFeatures)){
                List<Integer> featureIds=userFeatures.stream().map(OperationUserFeatures::getId).collect(Collectors.toList());
                List<Integer> typeIds=new ArrayList<>();
                typeIds.add(3);
                typeIds.add(4);
                List<TtDic> ttDicList=menuService.getMenuListByTypesAndFeatureIds(typeIds,featureIds);
                if(!CollectionUtils.isEmpty(ttDicList)){
                    List<TtDic> parentMenu=ttDicList.stream().filter(x->x.getParentId()==null).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(parentMenu)){
                        for (TtDic ttDic:parentMenu) {
                            UserMenuVo userMenuVo=new UserMenuVo();
                            BeanUtils.copyProperties(ttDic,userMenuVo);
                            List<TtDic> childrenMenu=ttDicList.stream().filter(x->(x.getParentId()!=null && x.getParentId().equals(ttDic.getId()))).collect(Collectors.toList());
                            if(!CollectionUtils.isEmpty(childrenMenu)){
                                List<ChildMenuVo> childMenuVos=new ArrayList<>();
                                for (TtDic ttDic1:childrenMenu) {
                                    ChildMenuVo childMenuVo=new ChildMenuVo();
                                    BeanUtils.copyProperties(ttDic1,childMenuVo);
                                    childMenuVos.add(childMenuVo);
                                }
                                userMenuVo.setChildrenMenuList(childMenuVos);
                            }
                            menuVos.add(userMenuVo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("查询用户菜单出现异常",e);
            return ResponseUtil.fail("失败");
        }
        return ResponseUtil.success(menuVos);
    }


}
