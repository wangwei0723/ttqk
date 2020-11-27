package com.haozi.ttqk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.model.TtDic;
import com.haozi.ttqk.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {
    @Resource
    MenuService menuService;

    @Test
    public void getMenuListByTypes(){
        List<Integer> typeList=new ArrayList<>();
        typeList.add(3);
        typeList.add(4);
        List<TtDic> ttDics=menuService.getMenuListByTypes(typeList);
        System.out.println(JSONObject.toJSONString(ttDics));
    }

}