package com.haozi.ttqk.controller;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.model.*;
import com.haozi.ttqk.service.ManagementService;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/manage")
@RestController
@Api(value = "运营管理", description = "运营管理", protocols = "http")
public class ManagementController {
    @Resource
    private ManagementService operationManagementService;

    @ApiOperation(value = "添加手机", httpMethod = "POST")
    @PostMapping("/saveMoblie")
    public ResultVo<String>  saveMoblie(PhoneVo phoneVo){
        try {
            if(phoneVo==null || phoneVo.getPhoneId()==null ){
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

    @ApiOperation(value = "保存评论", httpMethod = "POST")
    @PostMapping("/saveComment")
    public ResultVo<String>  saveComment(CommentVo commentVo){
        Integer commentId=null;
        try {
            if(commentVo==null || commentVo.getTagId()==null||commentVo.getType()==null|| StringUtils.isEmpty(commentVo.getComment())){
                log.info("必传参数为空");
                if(commentVo!=null){
                    log.info("添加用户信息[{}]", JSONObject.toJSONString(commentVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtComment ttComment=new TtComment();
            BeanUtils.copyProperties(commentVo,ttComment);
            commentId=operationManagementService.saveComment(ttComment);
        } catch (Exception e) {
            log.info("保存评论出现异常",e);
            return ResponseUtil.fail("保存评论失败");
        }
        return ResponseUtil.success(commentId);
    }

    @ApiOperation(value = "查询所有评论", httpMethod = "POST")
    @PostMapping("/getAllComment")
    public ResultVo<List<TagVo>>  getAllComment(){
        List<CommentVo> commentVos=new ArrayList<>();
        try {
            List<TtComment> ttComments= operationManagementService.getAllComment();
            if(!CollectionUtils.isEmpty(ttComments)){
                for (TtComment ttComment:ttComments) {
                    CommentVo commentVo=new CommentVo();
                    BeanUtils.copyProperties(ttComment,commentVo);
                    commentVos.add(commentVo);
                }
            }
        } catch (Exception e) {
            log.info("查询所有评论异常",e);
            return ResponseUtil.fail("查询所有评论失败");
        }
        return ResponseUtil.success(commentVos);
    }

    @ApiOperation(value = "添加养号任务", httpMethod = "POST")
    @PostMapping("/saveTaskTrainUser")
    public ResultVo<String>  saveTaskTrainUser(TaskTrainUserVo taskTrainUserVo){
        try {
            if(taskTrainUserVo==null || taskTrainUserVo.getTagId()==null ){
                log.info("必传参数为空");
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTaskTrainUser ttTaskTrainUser=new TtTaskTrainUser();
            BeanUtils.copyProperties(taskTrainUserVo,ttTaskTrainUser);
            operationManagementService.saveTaskTrainUser(ttTaskTrainUser);
        } catch (Exception e) {
            log.info("添加养号任务出现异常",e);
            return ResponseUtil.fail("添加养号任务失败");
        }
        return ResponseUtil.success("添加成功");
    }

    @ApiOperation(value = "查询养号任务", httpMethod = "POST")
    @PostMapping("/queryTaskTrainUser")
    public ResultVo<List<TaskTrainUserVo>>  queryTaskTrainUser(TaskTrainUserVo taskTrainUserVo){
        List<TaskTrainUserVo> taskTrainUserVos=new ArrayList<>();
        try {
            TtTaskTrainUser ttTaskTrainUser=new TtTaskTrainUser();
            if(taskTrainUserVo!=null){
                BeanUtils.copyProperties(taskTrainUserVo,ttTaskTrainUser);
            }
            List<TtTaskTrainUser> taskTrainUsers= operationManagementService.queryTaskTrainUser(ttTaskTrainUser);
            if(!CollectionUtils.isEmpty(taskTrainUsers)){
                Map<Integer,String> tagMap= operationManagementService.getTagMap();
                for (TtTaskTrainUser ttTaskTrainUser1:taskTrainUsers) {
                    TaskTrainUserVo taskTrainUserVo1=new TaskTrainUserVo();
                    BeanUtils.copyProperties(ttTaskTrainUser1,taskTrainUserVo1);
                    taskTrainUserVo1.setTagValue(tagMap.get(taskTrainUserVo1.getTagId()));
                    taskTrainUserVos.add(taskTrainUserVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询养号任务出现异常",e);
            return ResponseUtil.fail("查询养号任务失败");
        }
        return ResponseUtil.success(taskTrainUserVos);
    }

    @ApiOperation(value = "添加发送任务", httpMethod = "POST")
    @PostMapping("/saveTaskSend")
    public ResultVo<String>  saveTaskSend(TaskSendVo taskSendVo){
        try {
            if(taskSendVo==null || taskSendVo.getTagId()==null|| taskSendVo.getCommentId()==null ||taskSendVo.getSendTime()==null ){
                log.info("必传参数为空");
                if(taskSendVo!=null){
                    log.info("添加发送任务,[{}]",JSONObject.toJSONString(taskSendVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            if(taskSendVo.getStopTime()!=null){
                if(taskSendVo.getStopTime().getTime()<taskSendVo.getSendTime().getTime()){
                    return ResponseUtil.fail("发送结束时间不能早于开始时间");
                }
            }
            TtTaskSend ttTaskSend=new TtTaskSend();
            BeanUtils.copyProperties(taskSendVo,ttTaskSend);
            operationManagementService.saveTaskSend(ttTaskSend);
        } catch (Exception e) {
            log.info("添加发送任务出现异常",e);
            return ResponseUtil.fail("添加发送任务失败");
        }
        return ResponseUtil.success("添加成功");
    }

    @ApiOperation(value = "查询发送任务", httpMethod = "POST")
    @PostMapping("/queryTaskSend")
    public ResultVo<List<TaskSendVo>>  queryTaskSend(TaskSendVo taskSendVo){
        List<TaskSendVo> taskSendVos=new ArrayList<>();
        try {
            TtTaskSend ttTaskSend=new TtTaskSend();
            if(taskSendVo!=null){
                BeanUtils.copyProperties(taskSendVo,ttTaskSend);
            }
            List<TtTaskSend> ttTaskSends= operationManagementService.queryTaskSend(ttTaskSend);
            if(!CollectionUtils.isEmpty(ttTaskSends)){
                Map<Integer,String> tagMap= operationManagementService.getTagMap();
                Map<Integer,String> commentMap=operationManagementService.getCommentMap();
                for (TtTaskSend ttTaskSend1:ttTaskSends) {
                    TaskSendVo taskSendVo1=new TaskSendVo();
                    BeanUtils.copyProperties(ttTaskSend1,taskSendVo1);
                    taskSendVo1.setTagValue(tagMap.get(ttTaskSend1.getTagId()));
                    taskSendVo1.setComment(commentMap.get(ttTaskSend1.getCommentId()));
                    taskSendVos.add(taskSendVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询发送任务出现异常",e);
            return ResponseUtil.fail("查询发送任务失败");
        }
        return ResponseUtil.success(taskSendVos);
    }

    @ApiOperation(value = "保存添加粉丝", httpMethod = "POST")
    @PostMapping("/saveTaskAddFans")
    public ResultVo<String>  saveTaskAddFans(TaskAddFansVo taskAddFansVo){
        try {
            if(taskAddFansVo==null || taskAddFansVo.getTagId()==null|| taskAddFansVo.getAddDay()==null ||taskAddFansVo.getDelDay()==null|| taskAddFansVo.getDelDate()==null){
                log.info("必传参数为空");
                if(taskAddFansVo!=null){
                    log.info("添加发送任务,[{}]",JSONObject.toJSONString(taskAddFansVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTaskAddFans ttTaskAddFans=new TtTaskAddFans();
            BeanUtils.copyProperties(taskAddFansVo,ttTaskAddFans);
            operationManagementService.saveTaskAddFans(ttTaskAddFans);
        } catch (Exception e) {
            log.info("保存添加粉丝出现异常",e);
            return ResponseUtil.fail("保存添加粉丝失败");
        }
        return ResponseUtil.success("添加成功");
    }

    @ApiOperation(value = "查询添加粉丝", httpMethod = "POST")
    @PostMapping("/queryTaskAddFans")
    public ResultVo<List<TaskAddFansVo>>  queryTaskAddFans(TaskAddFansVo taskAddFansVo){
        List<TaskAddFansVo> taskAddFansVos=new ArrayList<>();
        try {
            TtTaskAddFans ttTaskAddFans=new TtTaskAddFans();
            if(taskAddFansVo!=null){
                BeanUtils.copyProperties(taskAddFansVo,ttTaskAddFans);
            }
            List<TtTaskAddFans> ttTaskAddFansList= operationManagementService.queryTaskAddFans(ttTaskAddFans);
            if(!CollectionUtils.isEmpty(ttTaskAddFansList)){
                Map<Integer,String> tagMap= operationManagementService.getTagMap();
                for (TtTaskAddFans ttTaskAddFans1:ttTaskAddFansList) {
                    TaskAddFansVo taskAddFansVo1=new TaskAddFansVo();
                    BeanUtils.copyProperties(ttTaskAddFans1,taskAddFansVo1);
                    taskAddFansVo1.setTagValue(tagMap.get(ttTaskAddFans1.getTagId()));
                    taskAddFansVos.add(taskAddFansVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询添加粉丝出现异常",e);
            return ResponseUtil.fail("查询添加粉丝失败");
        }
        return ResponseUtil.success(taskAddFansVos);
    }

}
