package com.haozi.ttqk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.mapper.TtFileMapper;
import com.haozi.ttqk.model.TtFile;
import com.haozi.ttqk.model.TtTag;
import com.haozi.ttqk.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Slf4j
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Resource
    private TtFileMapper ttFileMapper;


    public Integer saveFileInfo(TtFile ttFile){
        if(ttFile==null){
            log.info("tttTag为空");
            return null;
        }
        if(ttFile.getUuid()==null || ttFile.getFileDirectory()==null||ttFile.getFileName()==null || ttFile.getFileUrl()==null||ttFile.getTiktokId()==null){
            log.info("保存文件信息缺少必传参数,[{}]", JSONObject.toJSONString(ttFile));
            return null;
        }
        ttFileMapper.insertSelective(ttFile);
        return ttFile.getId();
    }

}
