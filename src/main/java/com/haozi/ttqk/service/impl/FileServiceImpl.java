package com.haozi.ttqk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.mapper.TtFileMapper;
import com.haozi.ttqk.model.TtFile;
import com.haozi.ttqk.model.TtPhone;
import com.haozi.ttqk.model.TtTag;
import com.haozi.ttqk.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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
        if(ttFile.getUuid()==null || ttFile.getFileDirectory()==null||ttFile.getFileName()==null || ttFile.getFileUrl()==null||ttFile.getTiktokUserId()==null){
            log.info("保存文件信息缺少必传参数,[{}]", JSONObject.toJSONString(ttFile));
            return null;
        }
        ttFileMapper.insertSelective(ttFile);
        return ttFile.getId();
    }
    public TtFile getFileByUuid(String uuid){
        Example example=new Example(TtFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andEqualTo("uuid",uuid);
        List<TtFile> ttFiles=ttFileMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(ttFiles)){
            return null;
        }
        return ttFiles.get(0);
    }

}
