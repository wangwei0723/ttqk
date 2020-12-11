package com.haozi.ttqk.mapper;

import com.haozi.ttqk.config.MyMapper;
import com.haozi.ttqk.model.TtTaskTrainUserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TtTaskTrainUserLogMapper extends MyMapper<TtTaskTrainUserLog> {

    List<TtTaskTrainUserLog> queryTaskTrainUserLog(@Param("userId") Integer userId, @Param("videoKeys")List<String> videoKeys, @Param("index")Integer index, @Param("pageSize")Integer pageSize);

}
