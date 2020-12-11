package com.haozi.ttqk.mapper;

import com.haozi.ttqk.config.MyMapper;
import com.haozi.ttqk.model.TiktokUser;
import org.apache.ibatis.annotations.Param;

public interface TiktokUserMapper extends MyMapper<TiktokUser> {
    Integer checkIsUserExist(@Param("userId") String userId, @Param("name")String name, @Param("tiktokId")String tiktokId);

}
