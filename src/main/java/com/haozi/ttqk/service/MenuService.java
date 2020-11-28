package com.haozi.ttqk.service;

import com.haozi.ttqk.model.OperationUserFeatures;
import com.haozi.ttqk.model.TtDic;

import java.util.List;

public interface MenuService {
    List<TtDic> getMenuListByTypesAndFeatureIds(List<Integer> typeIds,List<Integer> featureIds);
    List<TtDic> getMenuListByTypes(List<Integer> typeIds);
    List<OperationUserFeatures> getUserFeaturesByUserId(Integer userId);
    Boolean saveUserFeature(List<OperationUserFeatures> userFeatures,Integer userId);
}
