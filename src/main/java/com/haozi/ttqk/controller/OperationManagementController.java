package com.haozi.ttqk.controller;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.model.TiktokUser;
import com.haozi.ttqk.model.TtPhone;
import com.haozi.ttqk.model.TtTag;
import com.haozi.ttqk.model.TtVideo;
import com.haozi.ttqk.service.OperationManagementService;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/manage")
@RestController
@Api(value = "运营管理", description = "运营管理", protocols = "http")
public class OperationManagementController {
    @Resource
    private OperationManagementService operationManagementService;

    @ApiOperation(value = "添加手机", httpMethod = "POST")
    @PostMapping("/saveMoblie")
    public ResultVo<String>  saveMoblie(PhoneVo phoneVo){
        try {
            if(phoneVo==null || phoneVo.getPhoneId()==null || phoneVo.getPhoneModel()==null){
                log.info("必传参数为空");
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtPhone ttPhone=new TtPhone();
            BeanUtils.copyProperties(phoneVo,ttPhone);
            operationManagementService.savePhone(ttPhone);
        } catch (Exception e) {
            log.info("保存手机出现异常",e);
            return ResponseUtil.fail("保存手机失败");
        }
        return ResponseUtil.success(phoneVo.getPhoneId());
    }

    @ApiOperation(value = "查询手机", httpMethod = "POST")
    @PostMapping("/queryMoblie")
    public ResultVo<List<PhoneVo>>  queryMoblie(PhoneVo phoneVo){
        List<PhoneVo> phoneVos=new ArrayList<>();
        try {
            TtPhone ttPhone=new TtPhone();
            if(phoneVo!=null){
                BeanUtils.copyProperties(phoneVo,ttPhone);
            }
            List<TtPhone> ttPhones= operationManagementService.queryPhone(ttPhone);
            if(!CollectionUtils.isEmpty(ttPhones)){
                for (TtPhone ttPhone1:ttPhones) {
                    PhoneVo phoneVo1=new PhoneVo();
                    BeanUtils.copyProperties(ttPhone1,phoneVo1);
                    phoneVos.add(phoneVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询手机出现异常",e);
            return ResponseUtil.fail("查询手机失败");
        }
        return ResponseUtil.success(phoneVos);
    }

    @ApiOperation(value = "添加Tiktok用户", httpMethod = "POST")
    @PostMapping("/saveTiktokUser")
    public ResultVo<String>  saveTiktokUser(TikTokUserVo tikTokUserVo){
        try {
            if(tikTokUserVo==null || tikTokUserVo.getUserId()==null || tikTokUserVo.getName()==null || tikTokUserVo.getTiktokId()==null){
                log.info("必传参数为空");
                if(tikTokUserVo!=null){
                    log.info("添加用户信息[{}]", JSONObject.toJSONString(tikTokUserVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TiktokUser tiktokUser=new TiktokUser();
            BeanUtils.copyProperties(tikTokUserVo,tiktokUser);
            operationManagementService.saveTiktokUser(tiktokUser);
        } catch (Exception e) {
            log.info("保存tiktok用户出现异常",e);
            return ResponseUtil.fail("添加tiktok用户失败");
        }
        return ResponseUtil.success(tikTokUserVo.getUserId());
    }

    @ApiOperation(value = "查询Tiktok用户", httpMethod = "POST")
    @PostMapping("/queryTiktokUser")
    public ResultVo<List<TikTokUserVo>>  queryTiktokUser(TikTokUserVo tikTokUserVo){
        List<TikTokUserVo> tikTokUserVos=new ArrayList<>();
        try {
            TiktokUser tiktokUser=new TiktokUser();
            if(tikTokUserVo!=null){
                BeanUtils.copyProperties(tikTokUserVo,tiktokUser);
            }
            List<TiktokUser> tiktokUsers= operationManagementService.queryTiktokUser(tiktokUser);
            if(!CollectionUtils.isEmpty(tiktokUsers)){
                for (TiktokUser tiktokUser1:tiktokUsers) {
                    TikTokUserVo tikTokUserVo1=new TikTokUserVo();
                    BeanUtils.copyProperties(tiktokUser1,tikTokUserVo1);
                    tikTokUserVos.add(tikTokUserVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询tiktok用户异常",e);
            return ResponseUtil.fail("查询tiktok用户失败");
        }
        return ResponseUtil.success(tikTokUserVos);
    }

    @ApiOperation(value = "添加标签", httpMethod = "POST")
    @PostMapping("/saveTag")
    public ResultVo<String>  saveTag(TagVo tagVo){
        Integer tagId=null;
        try {
            if(tagVo==null || tagVo.getTagValue()==null ){
                log.info("必传参数为空");
                if(tagVo!=null){
                    log.info("添加用户信息[{}]", JSONObject.toJSONString(tagVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTag ttTag=new TtTag();
            BeanUtils.copyProperties(tagVo,ttTag);
            tagId=operationManagementService.saveTag(ttTag);
        } catch (Exception e) {
            log.info("保存标签出现异常",e);
            return ResponseUtil.fail("添加标签失败");
        }
        return ResponseUtil.success(tagId);
    }

    @ApiOperation(value = "查询所有标签", httpMethod = "POST")
    @PostMapping("/getAllTags")
    public ResultVo<List<TagVo>>  getAllTags(){
        List<TagVo> tagVos=new ArrayList<>();
        try {
            List<TtTag> ttTags= operationManagementService.getAllTag();
            if(!CollectionUtils.isEmpty(ttTags)){
                for (TtTag ttTag:ttTags) {
                    TagVo tagVo=new TagVo();
                    BeanUtils.copyProperties(ttTag,tagVo);
                    tagVos.add(tagVo);
                }
            }
        } catch (Exception e) {
            log.info("查询所有标签异常",e);
            return ResponseUtil.fail("查询所有标签失败");
        }
        return ResponseUtil.success(tagVos);
    }

    @ApiOperation(value = "根据标签ID查询标签", httpMethod = "POST")
    @PostMapping("/queryTagById")
    public ResultVo<TagVo>  queryTagById(Integer tagId){
        TagVo tagVo=null;
        try {
            if(tagId==null){
                log.info("tagId未空");
                return ResponseUtil.fail("标签ID不能为空");
            }
            TtTag ttTag= operationManagementService.getTagById(tagId);
            if(ttTag!=null){
                BeanUtils.copyProperties(ttTag,tagVo);
            }
        } catch (Exception e) {
            log.info("根据标签ID查询标签异常",e);
            return ResponseUtil.fail("根据标签ID查询标签失败");
        }
        return ResponseUtil.success(tagVo);
    }

    @ApiOperation(value = "上传视频", httpMethod = "POST")
    @PostMapping("/uploadVideo")
    public ResultVo<String>  uploadVideo(VideoVo videoVo){
        Integer videoId=null;
        try {
            if(videoVo==null || videoVo.getPath()==null||videoVo.getFaceImgPath()==null|| videoVo.getTagId()==null ||videoVo.getName()==null || videoVo.getUserId()==null ){
                log.info("必传参数为空");
                if(videoVo!=null){
                    log.info("添加用户信息[{}]", JSONObject.toJSONString(videoVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtVideo ttVideo=new TtVideo();
            BeanUtils.copyProperties(videoVo,ttVideo);
            ttVideo.setGreat(0);
            ttVideo.setComment(0);
            ttVideo.setUploadState(0);
            videoId=operationManagementService.uploadVideo(ttVideo);
        } catch (Exception e) {
            log.info("上传视频出现异常",e);
            return ResponseUtil.fail("上传视频失败");
        }
        return ResponseUtil.success(videoId);
    }


}
