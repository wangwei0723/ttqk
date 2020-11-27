package com.haozi.ttqk.controller;

import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.model.OperationUserFeatures;
import com.haozi.ttqk.model.TtDic;
import com.haozi.ttqk.service.MenuService;
import com.haozi.ttqk.service.UserService;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.ChildMenuVo;
import com.haozi.ttqk.vo.ResultVo;
import com.haozi.ttqk.vo.UserMenuVo;
import com.haozi.ttqk.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

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
                        List<TtDic> childrenMenu=ttDicList.stream().filter(x->x.getParentId().equals(ttDic.getId())).collect(Collectors.toList());
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
                            List<TtDic> childrenMenu=ttDicList.stream().filter(x->x.getParentId().equals(ttDic.getId())).collect(Collectors.toList());
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
