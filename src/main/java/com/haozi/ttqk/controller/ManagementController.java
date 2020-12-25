package com.haozi.ttqk.controller;

import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.model.*;
import com.haozi.ttqk.service.ManagementService;
import com.haozi.ttqk.util.DateUtils;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.util.Date;
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
            if(phoneVo.getId()==null && !StringUtils.isEmpty(phoneVo.getPhoneId())){
                List<TtPhone> ttPhones=operationManagementService.queryByPhoneId(phoneVo.getPhoneId());
                if(!CollectionUtils.isEmpty(ttPhones) && ttPhones.size()>0){
                    log.info("必传参数为空");
                    return ResponseUtil.fail("phoneId已存在，不能重复保存");
                }
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryMoblie")
    public ResultVo<PhoneResponseVo>  queryMoblie(PhoneVo phoneVo,Integer pageNo,Integer pageSize){
        PhoneResponseVo phoneResponseVo=null;
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TtPhone ttPhone=new TtPhone();
            if(phoneVo!=null){
                BeanUtils.copyProperties(phoneVo,ttPhone);
            }
            phoneResponseVo= operationManagementService.queryPhone(ttPhone,pageNo,pageSize);
        } catch (Exception e) {
            log.info("查询手机出现异常",e);
            return ResponseUtil.fail("查询手机失败");
        }
        return ResponseUtil.success(phoneResponseVo);
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
            if(tiktokUser.getId()==null){
                Integer num=operationManagementService.checkIsUserExist(tikTokUserVo.getUserId(),tikTokUserVo.getName(),tikTokUserVo.getTiktokId());
                if(num>0){
                    log.info("用户已存在,userId[{}],name[{}],tiktokId[{}]", tikTokUserVo.getUserId(),tikTokUserVo.getName(),tikTokUserVo.getTiktokId());
                    return ResponseUtil.fail("用户已存在，不能重复添加");
                }
            }
            operationManagementService.saveTiktokUser(tiktokUser);
        } catch (Exception e) {
            log.info("保存tiktok用户出现异常",e);
            return ResponseUtil.fail("添加tiktok用户失败");
        }
        return ResponseUtil.success(tikTokUserVo.getUserId());
    }

    @ApiOperation(value = "查询Tiktok用户", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTiktokUser")
    public ResultVo<TiktokUserResponseVo>  queryTiktokUser(TikTokUserVo tikTokUserVo,Integer pageNo,Integer pageSize){
        List<TikTokUserVo> tikTokUserVos=new ArrayList<>();
        TiktokUserResponseVo tiktokUserResponseVo=null;
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TiktokUser tiktokUser=new TiktokUser();
            if(tikTokUserVo!=null){
                BeanUtils.copyProperties(tikTokUserVo,tiktokUser);
            }
            tiktokUserResponseVo= operationManagementService.queryTiktokUser(tiktokUser,pageNo,pageSize);
        } catch (Exception e) {
            log.info("查询tiktok用户异常",e);
            return ResponseUtil.fail("查询tiktok用户失败");
        }
        return ResponseUtil.success(tiktokUserResponseVo);
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

    @ApiOperation(value = "查询标签", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tagId", value = "每页条数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tagValue", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTag")
    public ResultVo<TagResponseVo>  queryTag(Integer pageNo,Integer pageSize,Integer tagId,String tagValue){
        TagVo tagVo=new TagVo();
        TagResponseVo tagResponseVo=null;
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            tagResponseVo= operationManagementService.queryTag(tagId,tagValue,pageNo,pageSize);
        } catch (Exception e) {
            log.info("根据标签ID查询标签异常",e);
            return ResponseUtil.fail("根据标签ID查询标签失败");
        }
        return ResponseUtil.success(tagResponseVo);
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
            if(videoVo.getSendTime()!=null){
                ttVideo.setSendTime(new Date(videoVo.getSendTime()));
            }

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

    @ApiOperation(value = "查询评论", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "评论类型 0  养号评论 1,截流评论  2私信内容", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "评论内容，支持模糊查询", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getComment")
    public ResultVo<CommentResponseVo>  getComment(Integer tagId,Integer type,String comment,Integer pageNo,Integer pageSize){
        List<CommentVo> commentVos=new ArrayList<>();
        CommentResponseVo commentResponseVo=null;
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            commentResponseVo= operationManagementService.getComment(tagId,type,comment,pageNo,pageSize);
        } catch (Exception e) {
            log.info("查询所有评论异常",e);
            return ResponseUtil.fail("查询所有评论失败");
        }
        return ResponseUtil.success(commentResponseVo);
    }

    @ApiOperation(value = "随机获取评论", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签ID", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getRandomComment")
    public ResultVo<CommentVo>  getComment(Integer tagId){
        CommentVo commentVo=null;
        try {
            commentVo= operationManagementService.getRandomComment(tagId);
        } catch (Exception e) {
            log.info("随机获取评论异常",e);
            return ResponseUtil.fail("随机获取评论失败");
        }
        return ResponseUtil.success(commentVo);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTaskTrainUser")
    public ResultVo<List<TaskTrainUserVo>>  queryTaskTrainUser(TaskTrainUserVo taskTrainUserVo,Integer pageNo,Integer pageSize){
        List<TaskTrainUserVo> taskTrainUserVos=new ArrayList<>();
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TtTaskTrainUser ttTaskTrainUser=new TtTaskTrainUser();
            if(taskTrainUserVo!=null){
                BeanUtils.copyProperties(taskTrainUserVo,ttTaskTrainUser);
            }
            List<TtTaskTrainUser> taskTrainUsers= operationManagementService.queryTaskTrainUser(ttTaskTrainUser,pageNo,pageSize);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTaskSend")
    public ResultVo<List<TaskSendVo>>  queryTaskSend(TaskSendVo taskSendVo,Integer pageNo,Integer pageSize){
        List<TaskSendVo> taskSendVos=new ArrayList<>();
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TtTaskSend ttTaskSend=new TtTaskSend();
            if(taskSendVo!=null){
                BeanUtils.copyProperties(taskSendVo,ttTaskSend);
            }
            List<TtTaskSend> ttTaskSends= operationManagementService.queryTaskSend(ttTaskSend,pageNo,pageSize);
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

    @ApiOperation(value = "添加发送任务日志", httpMethod = "POST")
    @PostMapping("/saveTaskSendLog")
    public ResultVo<String>  saveTaskSendLog(TaskSendLogVo taskSendLogVo){
        try {
            if(taskSendLogVo==null || taskSendLogVo.getVideoId()==null|| taskSendLogVo.getTaskId()==null  ){
                log.info("必传参数为空");
                if(taskSendLogVo!=null){
                    log.info("添加发送任务日志,[{}]",JSONObject.toJSONString(taskSendLogVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTaskSendLog ttTaskSendLog=new TtTaskSendLog();
            BeanUtils.copyProperties(taskSendLogVo,ttTaskSendLog);
            operationManagementService.saveTaskSendLog(ttTaskSendLog);
        } catch (Exception e) {
            log.info("添加发送任务日志出现异常",e);
            return ResponseUtil.fail("添加发送任务日志失败");
        }
        return ResponseUtil.success("添加成功");
    }

    @ApiOperation(value = "查询发送任务日志", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTaskSendLog")
    public ResultVo<List<TaskSendLogVo>>  queryTaskSendLog(TaskSendLogVo taskSendLogVo,Integer pageNo,Integer pageSize){
        List<TaskSendLogVo> taskSendLogVos=new ArrayList<>();
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TtTaskSendLog ttTaskSendLog=new TtTaskSendLog();
            if(taskSendLogVo!=null){
                BeanUtils.copyProperties(taskSendLogVo,ttTaskSendLog);
            }
            List<TtTaskSendLog> ttTaskSendLogs= operationManagementService.queryTaskSendLog(ttTaskSendLog,pageNo,pageSize);
            if(!CollectionUtils.isEmpty(ttTaskSendLogs)){
                for (TtTaskSendLog ttTaskSendLog1:ttTaskSendLogs) {
                    TaskSendLogVo taskSendLogVo1=new TaskSendLogVo();
                    BeanUtils.copyProperties(ttTaskSendLog1,taskSendLogVo1);
                    taskSendLogVos.add(taskSendLogVo1);
                }
            }
        } catch (Exception e) {
            log.info("查询发送任务日志出现异常",e);
            return ResponseUtil.fail("查询发送任务日志失败");
        }
        return ResponseUtil.success(taskSendLogVos);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTaskAddFans")
    public ResultVo<TaskAddFansResponseVo>  queryTaskAddFans(TaskAddFansVo taskAddFansVo,Integer pageNo,Integer pageSize){
        TaskAddFansResponseVo taskAddFansResponseVo=null;
        List<TaskAddFansVo> taskAddFansVos=new ArrayList<>();
        try {
            TtTaskAddFans ttTaskAddFans=new TtTaskAddFans();
            if(taskAddFansVo!=null){
                BeanUtils.copyProperties(taskAddFansVo,ttTaskAddFans);
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            taskAddFansResponseVo= operationManagementService.queryTaskAddFans(ttTaskAddFans,pageNo,pageSize);
        } catch (Exception e) {
            log.info("查询添加粉丝出现异常",e);
            return ResponseUtil.fail("查询添加粉丝失败");
        }
        return ResponseUtil.success(taskAddFansResponseVo);
    }

    @ApiOperation(value = "查询用户未上传视频", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryUserUnUploadVideo")
    public ResultVo<List<VideoVo>>  queryUserUnUploadVideo(String userId,Integer pageNo,Integer pageSize){
        List<VideoVo> videoVos=new ArrayList<>();
        try {
            if(userId==null){
                log.info("用户id不能为空");
                return ResponseUtil.fail("userId不能为空");
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            List<TtVideo> ttVideos= operationManagementService.queryUserUnUploadVideo(userId,pageNo,pageSize);
            if(!CollectionUtils.isEmpty(ttVideos)){
                Map<Integer,String> tagMap= operationManagementService.getTagMap();
                for (TtVideo ttVideo:ttVideos) {
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(ttVideo,videoVo);
                    videoVo.setTagValue(tagMap.get(ttVideo.getTagId()));
                    videoVo.setSendTime(ttVideo.getSendTime().getTime());
                    videoVos.add(videoVo);
                }
            }
        } catch (Exception e) {
            log.info("查询用户未上传视频出现异常",e);
            return ResponseUtil.fail("查询用户未上传视频失败");
        }
        return ResponseUtil.success(videoVos);
    }

    @ApiOperation(value = "查询视频列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryVideo")
    public ResultVo<VideoResponseVo>  queryUserUnUploadVideo(VideoVo videoVo,Integer pageNo,Integer pageSize){
        List<VideoVo> videoVos=new ArrayList<>();
        VideoResponseVo videoResponseVo=null;
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            TtVideo ttVideo=new TtVideo();
            if(videoVo!=null){
                BeanUtils.copyProperties(videoVo,ttVideo);
            }
            videoResponseVo= operationManagementService.queryVideo(ttVideo,pageNo,pageSize);
        } catch (Exception e) {
            log.info("查询视频列表出现异常",e);
            return ResponseUtil.fail("查询视频列表失败");
        }
        return ResponseUtil.success(videoResponseVo);
    }


    @ApiOperation(value = "更新视频上传状态", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频ID", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/updateVideoUploadState")
    public ResultVo<Boolean>  updateVideoUploadState(Integer id){
        List<TaskAddFansVo> taskAddFansVos=new ArrayList<>();
        try {
            if(id==null){
                log.info("视频id不能为空");
                return ResponseUtil.fail("ID不能为空");
            }
            Boolean flag= operationManagementService.updateVideoUploadState(id);
            if(!flag){
                return ResponseUtil.fail("更新视频上传状态失败");
            }
        } catch (Exception e) {
            log.info("更新视频上传状态出现异常",e);
            return ResponseUtil.fail("更新视频上传状态失败");
        }
        return ResponseUtil.success(true);
    }

    @ApiOperation(value = "保存TiktokVideo", httpMethod = "POST")
    @PostMapping("/saveTiktokVideo")
    public ResultVo<String>  saveTiktokVideo(TiktokVideoVo tiktokVideoVo){
        try {
            if(tiktokVideoVo==null || tiktokVideoVo.getTagId()==null|| tiktokVideoVo.getName()==null){
                log.info("必传参数为空");
                if(tiktokVideoVo!=null){
                    log.info("添加发送任务,[{}]",JSONObject.toJSONString(tiktokVideoVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTiktokVideo ttTiktokVideo=new TtTiktokVideo();
            BeanUtils.copyProperties(tiktokVideoVo,ttTiktokVideo);
            Boolean flag=operationManagementService.saveTiktokVideo(ttTiktokVideo);
            if(!flag){
                log.info("保存TiktokVideo失败");
                return ResponseUtil.fail("保存TiktokVideo失败");
            }
        } catch (Exception e) {
            log.info("保存TiktokVideo出现异常",e);
            return ResponseUtil.fail("保存TiktokVideo失败");
        }
        return ResponseUtil.success("保存成功");
    }

    @ApiOperation(value = "保存TiktokAccount", httpMethod = "POST")
    @PostMapping("/saveTiktokAccount")
    public ResultVo<String>  saveTiktokAccount(TiktokAccountVo tiktokAccountVo){
        try {
            if(tiktokAccountVo==null || tiktokAccountVo.getTagId()==null|| tiktokAccountVo.getTiktokName()==null){
                log.info("必传参数为空");
                if(tiktokAccountVo!=null){
                    log.info("添加发送任务,[{}]",JSONObject.toJSONString(tiktokAccountVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTiktokAccount ttTiktokAccount=new TtTiktokAccount();
            BeanUtils.copyProperties(tiktokAccountVo,ttTiktokAccount);
            Boolean flag=operationManagementService.saveTiktokAccount(ttTiktokAccount);
            if(!flag){
                log.info("保存TiktokAccount失败");
                return ResponseUtil.fail("保存TiktokAccount失败");
            }
        } catch (Exception e) {
            log.info("保存TiktokAccount出现异常",e);
            return ResponseUtil.fail("保存TiktokAccount失败");
        }
        return ResponseUtil.success("保存成功");
    }
    @ApiOperation(value = "保存TaskTrainUserLog", httpMethod = "POST")
    @PostMapping("/saveTaskTrainUserLog")
    public ResultVo<String>  saveTaskTrainUserLog(TaskTrainUserLogVo taskTrainUserLogVo){
        try {
            if(taskTrainUserLogVo==null || taskTrainUserLogVo.getUserId()==null|| StringUtils.isEmpty(taskTrainUserLogVo.getVideoKey())){
                log.info("必传参数为空");
                if(taskTrainUserLogVo!=null){
                    log.info("添加TaskTrainUserLog,[{}]",JSONObject.toJSONString(taskTrainUserLogVo));
                }
                return ResponseUtil.fail("必传参数不能为空");
            }
            TtTaskTrainUserLog ttTaskTrainUserLog=new TtTaskTrainUserLog();
            BeanUtils.copyProperties(taskTrainUserLogVo,ttTaskTrainUserLog);
            if(!StringUtils.isEmpty(taskTrainUserLogVo.getCommentTime())){
                ttTaskTrainUserLog.setCommentTime(DateUtils.parseDateSecondFormat(taskTrainUserLogVo.getCommentTime()));
            }
            Boolean flag=operationManagementService.saveTaskTrainUserLog(ttTaskTrainUserLog);
            if(!flag){
                log.info("保存TaskTrainUserLog失败");
                return ResponseUtil.fail("保存TaskTrainUserLog失败");
            }
        } catch (Exception e) {
            log.info("保存TaskTrainUserLog出现异常",e);
            return ResponseUtil.fail("保存TaskTrainUserLog失败");
        }
        return ResponseUtil.success("保存成功");
    }

    @ApiOperation(value = "查询TaskTrainUserLog", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoKey", value = "userId", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping("/queryTaskTrainUserLog")
    public ResultVo<List<TaskTrainUserLogVo>>  queryTaskTrainUserLog(Integer userId,String videoKey,Integer pageNo,Integer pageSize){
        List<TaskTrainUserLogVo> taskTrainUserLogVos=new ArrayList<>();
        try {
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=50;
            }
            List<TtTaskTrainUserLog> ttTaskTrainUserLogs= operationManagementService.queryTaskTrainUserLog(userId,videoKey,pageNo,pageSize);
            if(!CollectionUtils.isEmpty(ttTaskTrainUserLogs)){
                Map<Integer,String> tagMap= operationManagementService.getTagMap();
                for (TtTaskTrainUserLog ttTaskTrainUserLog:ttTaskTrainUserLogs) {
                    TaskTrainUserLogVo taskTrainUserLogVo=new TaskTrainUserLogVo();
                    BeanUtils.copyProperties(ttTaskTrainUserLog,taskTrainUserLogVo);
                    if(ttTaskTrainUserLog.getCommentTime()!=null){
                        taskTrainUserLogVo.setCommentTime(DateUtils.dateToFormatString(ttTaskTrainUserLog.getCommentTime()));
                    }
                    if(ttTaskTrainUserLog.getTagId()!=null){
                        taskTrainUserLogVo.setTagValue(tagMap.get(ttTaskTrainUserLog.getTagId()));
                    }
                    taskTrainUserLogVos.add(taskTrainUserLogVo);
                }
            }
        } catch (Exception e) {
            log.info("查询TaskTrainUserLog出现异常",e);
            return ResponseUtil.fail("查询TaskTrainUserLog失败");
        }
        return ResponseUtil.success(taskTrainUserLogVos);
    }

}
