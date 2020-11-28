package com.haozi.ttqk.service.impl;

import com.haozi.ttqk.mapper.OperationUserFeaturesMapper;
import com.haozi.ttqk.mapper.TtDicMapper;
import com.haozi.ttqk.model.OperationUserFeatures;
import com.haozi.ttqk.model.TtDic;
import com.haozi.ttqk.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource
    TtDicMapper ttDicMapper;
    @Resource
    OperationUserFeaturesMapper operationUserFeaturesMapper;
    @Override
    public List<TtDic> getMenuListByTypesAndFeatureIds(List<Integer> typeIds,List<Integer> featureIds) {
        Example example=new Example(TtDic.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andIn("typeId",typeIds);
        criteria.andIn("id",featureIds);
        example.setOrderByClause("id asc");
        List<TtDic> ttDicList=ttDicMapper.selectByExample(example);
        return ttDicList;
    }

    public List<TtDic> getMenuListByTypes(List<Integer> typeIds) {
        Example example=new Example(TtDic.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andIn("typeId",typeIds);
        example.setOrderByClause("id asc");
        List<TtDic> ttDicList=ttDicMapper.selectByExample(example);
        return ttDicList;
    }

    public List<OperationUserFeatures> getUserFeaturesByUserId(Integer userId){
        Example example=new Example(OperationUserFeatures.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andEqualTo("userId",userId);
         return operationUserFeaturesMapper.selectByExample(example);
    }
    public Boolean saveUserFeature(List<OperationUserFeatures> userFeatures,Integer userId){
        try {
            Example example=new Example(OperationUserFeatures.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isDelete",0);
            criteria.andEqualTo("userId",userId);
            OperationUserFeatures operationUserFeatures=new OperationUserFeatures();
            operationUserFeatures.setIsDelete(1);
            operationUserFeaturesMapper.updateByCondition(operationUserFeatures,example);
            if(CollectionUtils.isEmpty(userFeatures)){
                return true;
            }
//            List<OperationUserFeatures> userCurrentFeatures=getUserFeaturesByUserId(userId);
//            List<Integer> userFeatureIds=null;
//            if(!CollectionUtils.isEmpty(userCurrentFeatures)){
//                userFeatureIds=userCurrentFeatures.stream().map(OperationUserFeatures::getFeaturesId).collect(Collectors.toList());
//            }
//            if(userFeatureIds==null){
//                userFeatureIds=new ArrayList<>();
//            }
//            List<OperationUserFeatures> userNewFeatures=new ArrayList<>();
//            for (OperationUserFeatures userFeatures1:userFeatures) {
//                if(!userFeatureIds.contains(userFeatures1.getFeaturesId())) {
//                    userNewFeatures.add(userFeatures1);
//                }
//            }

            if(!CollectionUtils.isEmpty(userFeatures)){
                operationUserFeaturesMapper.insertList(userFeatures);
            }
        } catch (Exception e) {
            log.info("保存权限出现异常",e);
            return false;
        }
        return true;
    }

}
