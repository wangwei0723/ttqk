package com.haozi.ttqk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haozi.ttqk.mapper.*;
import com.haozi.ttqk.model.*;
import com.haozi.ttqk.service.ManagementService;
import com.haozi.ttqk.util.DateUtils;
import com.haozi.ttqk.util.Md5Util;
import com.haozi.ttqk.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("operationManagementService")
public class ManagementServiceImpl implements ManagementService {
    @Resource
    private TtPhoneMapper ttPhoneMapper;
    @Resource
    private TiktokUserMapper tiktokUserMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private TtTaskTrainUserMapper ttTaskTrainUserMapper;
    @Resource
    private TtTaskSendMapper ttTaskSendMapper;
    @Resource
    private TtTaskAddFansMapper ttTaskAddFansMapper;
    @Resource
    private TiktokVideoMapper tiktokVideoMapper;
    @Resource
    private TiktokAccountMapper tiktokAccountMapper;
    @Resource
    private TtTaskTrainUserLogMapper ttTaskTrainUserLogMapper;
    @Resource
    private TtTaskSendLogMapper ttTaskSendLogMapper;

    public Boolean savePhone(TtPhone ttPhone){
        if(ttPhone==null){
            log.info("ttPhone为空");
            return false;
        }
        if(ttPhone.getId()==null){
            ttPhoneMapper.insertSelective(ttPhone);
        }else {
            ttPhoneMapper.updateByPrimaryKeySelective(ttPhone);
        }
        return true;
    }

    public List<TtPhone> queryByPhoneId(String phoneId){
        Example example=new Example(TtPhone.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phoneId",phoneId);
        criteria.andEqualTo("isDelete",0);
        return ttPhoneMapper.selectByExample(example);
    }

    public PhoneResponseVo queryPhone(TtPhone ttPhone, Integer pageNo, Integer pageSize){
        PhoneResponseVo phoneResponseVo=new PhoneResponseVo();
        List<PhoneVo> phoneVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtPhone.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttPhone!=null){
            if(ttPhone.getPhoneId()!=null){
                criteria.andLike("phoneId","%"+ttPhone.getPhoneId()+"%");
            }
            if(ttPhone.getUuid()!=null){
                criteria.andLike("phoneModel","%"+ttPhone.getPhoneModel()+"%");
            }
        }
        criteria.andEqualTo("isDelete",0);
        PageInfo<TtPhone> pageInfo=new PageInfo<TtPhone>(ttPhoneMapper.selectByExample(example));
        List<TtPhone> ttPhones=pageInfo.getList();
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        if(!CollectionUtils.isEmpty(ttPhones)){
            for (TtPhone ttPhone1:ttPhones) {
                PhoneVo phoneVo1=new PhoneVo();
                BeanUtils.copyProperties(ttPhone1,phoneVo1);
                phoneVo1.setUpdateTime(ttPhone1.getUpdateTime().getTime());
                phoneVos.add(phoneVo1);
            }
        }
        phoneResponseVo.setTotalNum(totalNum);
        phoneResponseVo.setPhoneList(phoneVos);
        return phoneResponseVo;
    }

    public Boolean saveTiktokUser(TiktokUser tiktokUser){
        if(tiktokUser==null){
            log.info("tiktokUser为空");
            return false;
        }
        if(tiktokUser.getId()==null){
            tiktokUserMapper.insertSelective(tiktokUser);
        }else {
            tiktokUserMapper.updateByPrimaryKeySelective(tiktokUser);
        }
        return true;
    }

    public Integer checkIsUserExist(String userId, String name, String tiktokId){
        return tiktokUserMapper.checkIsUserExist(userId,name,tiktokId);
    }

    public TiktokUserResponseVo queryTiktokUser(TiktokUser tiktokUser,Integer pageNo,Integer pageSize){
        TiktokUserResponseVo tiktokUserResponseVo=new TiktokUserResponseVo();
        List<TikTokUserVo> tikTokUserVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TiktokUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        if(tiktokUser!=null){
            if(!StringUtils.isEmpty(tiktokUser.getUserId())){
                criteria.andLike("userId","%"+tiktokUser.getUserId()+"%");
            }
            if(!StringUtils.isEmpty(tiktokUser.getName())){
                criteria.andLike("name","%"+tiktokUser.getName()+"%");
            }
            if(!StringUtils.isEmpty(tiktokUser.getTiktokId())){
                criteria.andEqualTo("tiktokId",tiktokUser.getTiktokId());
            }
            if(!StringUtils.isEmpty(tiktokUser.getPhone())){
                criteria.andEqualTo("phone",tiktokUser.getPhone());
            }
            if(!StringUtils.isEmpty(tiktokUser.getPhoneId())){
                criteria.andLike("phoneId","%"+tiktokUser.getPhoneId()+"%");
            }
            if(tiktokUser.getStatus()!=null){
                criteria.andEqualTo("status",tiktokUser.getStatus());
            }
            if(tiktokUser.getTagId()!=null){
                criteria.andEqualTo("tagId",tiktokUser.getTagId());
            }
            if(tiktokUser.getMerchantId()!=null){
                criteria.andEqualTo("merchantId",tiktokUser.getMerchantId());
            }
        }
        PageInfo pageInfo=new PageInfo<TiktokUser>(tiktokUserMapper.selectByExample(example));
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        List<TiktokUser> tiktokUsers=pageInfo.getList();
        if(!CollectionUtils.isEmpty(tiktokUsers)){
            Map<Integer,String> tagMap= getTagMap();
            for (TiktokUser tiktokUser1:tiktokUsers) {
                TikTokUserVo tikTokUserVo1=new TikTokUserVo();
                BeanUtils.copyProperties(tiktokUser1,tikTokUserVo1);
                tikTokUserVo1.setTagValue(tagMap.get(tiktokUser1.getTagId()));
                tikTokUserVo1.setCreatedTime(DateUtils.dateToFormatString(tiktokUser1.getCreatedTime()));
                tikTokUserVos.add(tikTokUserVo1);
            }
        }
        tiktokUserResponseVo.setTotalNum(totalNum);
        tiktokUserResponseVo.setTiktokUserList(tikTokUserVos);
        return tiktokUserResponseVo;
    }


    public Integer saveTag(TtTag ttTag){
        if(ttTag==null){
            log.info("tttTag为空");
            return null;
        }
        if(ttTag.getId()==null){
            tagMapper.insertSelective(ttTag);
        }else {
            tagMapper.updateByPrimaryKeySelective(ttTag);
        }
        return ttTag.getId();
    }

    public List<TtTag> getAllTag(){
        Example example=new Example(TtTag.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        List<TtTag> ttTags=tagMapper.selectByExample(example);
        return ttTags;
    }

    public Map<Integer,String> getTagMap(){
        Map<Integer,String> map=new HashMap();
        List<TtTag> allTags=getAllTag();
        if(!CollectionUtils.isEmpty(allTags)){
            for (TtTag ttTag:allTags) {
                map.put(ttTag.getId(),ttTag.getTagValue());
            }
        }
        return map;
    }

    public TagResponseVo queryTag(Integer tagId,String tagValue,Integer pageNo,Integer pageSize){
        TagResponseVo tagResponseVo=new TagResponseVo();
        List<TagVo> tagVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtTag.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        if(tagId!=null){
            criteria.andEqualTo("id",tagId);
        }
        if(!StringUtils.isEmpty(tagValue)){
            criteria.andLike("tagValue","%"+tagValue+"%");
        }
        PageInfo pageInfo=new PageInfo<TtTag>(tagMapper.selectByExample(example));
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        List<TtTag> ttTags=pageInfo.getList();
        if(!CollectionUtils.isEmpty(ttTags)){
            for (TtTag ttTag:ttTags) {
                TagVo tagVo=new TagVo();
                BeanUtils.copyProperties(ttTag,tagVo);
                tagVos.add(tagVo);
            }
        }
        tagResponseVo.setTotalNum(totalNum);
        tagResponseVo.setTagList(tagVos);

        return tagResponseVo;
    }


    public Integer uploadVideo(TtVideo ttVideo){
        if(ttVideo==null){
            log.info("ttVideo为空");
            return null;
        }
        if(ttVideo.getId()==null){
            videoMapper.insertSelective(ttVideo);
        }else {
            videoMapper.updateByPrimaryKeySelective(ttVideo);
        }
        return ttVideo.getId();
    }

    public Integer saveComment(TtComment ttComment){
        if(ttComment==null){
            log.info("ttComment为空");
            return null;
        }
        if(ttComment.getId()==null){
            commentMapper.insertSelective(ttComment);
        }else {
            commentMapper.updateByPrimaryKeySelective(ttComment);
        }
        return ttComment.getId();
    }

    public CommentResponseVo getComment(Integer tagId, Integer type, String comment, Integer pageNo, Integer pageSize){
        CommentResponseVo commentResponseVo=new CommentResponseVo();
        List<CommentVo> commentVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        if(tagId!=null){
            criteria.andEqualTo("tagId",tagId);
        }
        if(type!=null){
            criteria.andEqualTo("type",type);
        }
        if(!StringUtils.isEmpty(comment)){
            criteria.andLike("comment","%"+comment+"%");
        }
        example.setOrderByClause("created_time desc");
        PageInfo pageInfo=new PageInfo<TtComment>(commentMapper.selectByExample(example));
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        List<TtComment> ttComments=pageInfo.getList();
        if(!CollectionUtils.isEmpty(ttComments)){
            for (TtComment ttComment:ttComments) {
                CommentVo commentVo=new CommentVo();
                BeanUtils.copyProperties(ttComment,commentVo);
                commentVos.add(commentVo);
            }
        }
        commentResponseVo.setTotalNum(totalNum);
        commentResponseVo.setCommentList(commentVos);
        return commentResponseVo;
    }

    public CommentVo getRandomComment(Integer tagId){
        Example example=new Example(TtComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        if(tagId!=null){
            criteria.andEqualTo("tagId",tagId);
        }
        List<TtComment> ttComments=commentMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(ttComments)){
            return null;
        }
        Integer num=ttComments.size();
        Integer index=(int) (Math.random()*num);
        TtComment ttComment=ttComments.get(index);
        CommentVo commentVo=new CommentVo();
        if(ttComment!=null){
            BeanUtils.copyProperties(ttComment,commentVo);
        }
        return commentVo;
    }

    public List<TtComment> getAllComment(){
        Example example=new Example(TtComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        List<TtComment> ttComments=commentMapper.selectByExample(example);
        return ttComments;
    }

    public Map<Integer,String> getCommentMap(){
        Map<Integer,String> map=new HashMap();
        List<TtComment> allComment=getAllComment();
        if(!CollectionUtils.isEmpty(allComment)){
            for (TtComment ttComment:allComment) {
                map.put(ttComment.getId(),ttComment.getComment());
            }
        }
        return map;
    }


    public Boolean saveTaskTrainUser(TtTaskTrainUser ttTaskTrainUser){
        if(ttTaskTrainUser==null){
            log.info("ttTaskTrainUser为空");
            return false;
        }
        if(ttTaskTrainUser.getId()==null){
            ttTaskTrainUserMapper.insertSelective(ttTaskTrainUser);
        }else {
            ttTaskTrainUserMapper.updateByPrimaryKeySelective(ttTaskTrainUser);
        }
        return true;
    }

    public List<TtTaskTrainUser> queryTaskTrainUser(TtTaskTrainUser ttTaskTrainUser,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtTaskTrainUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskTrainUser!=null){
            if(ttTaskTrainUser.getTagId()!=null){
                criteria.andEqualTo("tagId",ttTaskTrainUser.getTagId());
            }
        }
        criteria.andEqualTo("isDelete",0);
        List<TtTaskTrainUser> taskTrainUsers=new PageInfo<TtTaskTrainUser>(ttTaskTrainUserMapper.selectByExample(example)).getList();
        return taskTrainUsers;
    }

    public Boolean saveTaskSend(TtTaskSend ttTaskSend){
        if(ttTaskSend==null){
            log.info("ttTaskSend为空");
            return false;
        }
        if(ttTaskSend.getId()==null){
            ttTaskSendMapper.insertSelective(ttTaskSend);
        }else {
            ttTaskSendMapper.updateByPrimaryKeySelective(ttTaskSend);
        }
        return true;
    }

    public List<TtTaskSend> queryTaskSend(TtTaskSend ttTaskSend,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtTaskSend.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskSend!=null){
            if(ttTaskSend.getTagId()!=null){
                criteria.andEqualTo("tagId",ttTaskSend.getTagId());
            }
            if(ttTaskSend.getType()!=null){
                criteria.andEqualTo("type",ttTaskSend.getType());
            }
            if(ttTaskSend.getCommentId()!=null){
                criteria.andEqualTo("commentId",ttTaskSend.getCommentId());
            }
        }
        List<TtTaskSend> ttTaskSends=new PageInfo<TtTaskSend>(ttTaskSendMapper.selectByExample(example)).getList();
        return ttTaskSends;
    }

    public Boolean saveTaskSendLog(TtTaskSendLog ttTaskSendLog){
        if(ttTaskSendLog==null){
            log.info("ttTaskSendLog为空");
            return false;
        }
        if(ttTaskSendLog.getId()==null){
            ttTaskSendLogMapper.insertSelective(ttTaskSendLog);
        }else {
            ttTaskSendLogMapper.updateByPrimaryKeySelective(ttTaskSendLog);
        }
        return true;
    }

    public List<TtTaskSendLog> queryTaskSendLog(TtTaskSendLog ttTaskSendLog,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtTaskSendLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskSendLog!=null){
            if(ttTaskSendLog.getTaskId()!=null){
                criteria.andEqualTo("taskId",ttTaskSendLog.getTaskId());
            }
            if(ttTaskSendLog.getVideoId()!=null){
                criteria.andEqualTo("videoId",ttTaskSendLog.getVideoId());
            }
            if(ttTaskSendLog.getAccountId()!=null){
                criteria.andEqualTo("accountId",ttTaskSendLog.getAccountId());
            }
            if(ttTaskSendLog.getUserId()!=null){
                criteria.andEqualTo("userId",ttTaskSendLog.getUserId());
            }
        }
        List<TtTaskSendLog> ttTaskSendLogs=new PageInfo<TtTaskSendLog>(ttTaskSendLogMapper.selectByExample(example)).getList();
        return ttTaskSendLogs;
    }

    public Boolean saveTaskAddFans(TtTaskAddFans ttTaskAddFans){
        if(ttTaskAddFans==null){
            log.info("ttTaskAddFans为空");
            return false;
        }
        if(ttTaskAddFans.getId()==null){
            ttTaskAddFansMapper.insertSelective(ttTaskAddFans);
        }else {
            ttTaskAddFansMapper.updateByPrimaryKeySelective(ttTaskAddFans);
        }
        return true;
    }

    public TaskAddFansResponseVo queryTaskAddFans(TtTaskAddFans ttTaskAddFans,Integer pageNo,Integer pageSize){
        TaskAddFansResponseVo taskAddFansResponseVo=new TaskAddFansResponseVo();
        List<TaskAddFansVo> taskAddFansVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtTaskAddFans.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskAddFans!=null){
            if(ttTaskAddFans.getTagId()!=null){
                criteria.andEqualTo("tagId",ttTaskAddFans.getTagId());
            }
        }
        PageInfo pageInfo=new PageInfo<TtTaskAddFans>(ttTaskAddFansMapper.selectByExample(example));
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        List<TtTaskAddFans> ttTaskAddFansList=pageInfo.getList();
        if(!CollectionUtils.isEmpty(ttTaskAddFansList)){
            Map<Integer,String> tagMap=getTagMap();
            for (TtTaskAddFans ttTaskAddFans1:ttTaskAddFansList) {
                TaskAddFansVo taskAddFansVo1=new TaskAddFansVo();
                BeanUtils.copyProperties(ttTaskAddFans1,taskAddFansVo1);
                taskAddFansVo1.setTagValue(tagMap.get(ttTaskAddFans1.getTagId()));
                taskAddFansVos.add(taskAddFansVo1);
            }
        }
        taskAddFansResponseVo.setTotalNum(totalNum);
        taskAddFansResponseVo.setTaskAddFansList(taskAddFansVos);
        return taskAddFansResponseVo;
    }

    public List<TtVideo> queryUserUnUploadVideo(String userId,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtVideo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("uploadState",0);
        criteria.andEqualTo("isDelete",0);
        List<TtVideo> ttVideos=new PageInfo<TtVideo>(videoMapper.selectByExample(example)).getList();
        return ttVideos;
    }

    public VideoResponseVo queryVideo(TtVideo ttVideo, Integer pageNo, Integer pageSize){
        VideoResponseVo videoResponseVo=new VideoResponseVo();
        List<VideoVo> videoVos=new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtVideo.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(ttVideo.getUserId())){
            criteria.andLike("userId","%"+ttVideo.getUserId()+"%");
        }
        if(ttVideo.getTagId()!=null){
            criteria.andEqualTo("tagId",ttVideo.getTagId());
        }
        if(ttVideo.getUploadState()!=null){
            criteria.andEqualTo("uploadState",ttVideo.getUploadState());
        }
        criteria.andEqualTo("isDelete",0);
        PageInfo pageInfo=new PageInfo<TtVideo>(videoMapper.selectByExample(example));
        Integer totalNum=((Long) pageInfo.getTotal()).intValue();
        List<TtVideo> ttVideos=pageInfo.getList();
        if(!CollectionUtils.isEmpty(ttVideos)){
            Map<Integer,String> tagMap= getTagMap();
            for (TtVideo ttVideo1:ttVideos) {
                VideoVo videoVo=new VideoVo();
                BeanUtils.copyProperties(ttVideo1,videoVo);
                videoVo.setTagValue(tagMap.get(ttVideo1.getTagId()));
                videoVo.setSendTime(ttVideo1.getSendTime().getTime());
                videoVos.add(videoVo);
            }
        }
        videoResponseVo.setTotalNum(totalNum);
        videoResponseVo.setVideoList(videoVos);
        return videoResponseVo;
    }

    public List<TtVideo> queryUserUnUploadVideo(TtVideo ttVideo,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Example example=new Example(TtVideo.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttVideo.getUserId()!=null){
            criteria.andEqualTo("userId",ttVideo.getUserId());
        }
        if(ttVideo.getTagId()!=null){
            criteria.andEqualTo("tagId",ttVideo.getTagId());
        }

        criteria.andEqualTo("uploadState",0);
        criteria.andEqualTo("isDelete",0);
        List<TtVideo> ttVideos=new PageInfo<TtVideo>(videoMapper.selectByExample(example)).getList();
        return ttVideos;
    }

    public Boolean updateVideoUploadState(Integer id){
        TtVideo ttVideo=videoMapper.selectByPrimaryKey(id);
        if(ttVideo==null){
            log.info("要更新的视频不存在");
            return false;
        }
        ttVideo.setUploadState(1);
        int num=videoMapper.updateByPrimaryKeySelective(ttVideo);
        if(num<1){
            log.info("更新失败");
            return false;
        }
        return true;
    }

    public Boolean saveTiktokVideo(TtTiktokVideo ttTiktokVideo){
        if(ttTiktokVideo==null){
            log.info("ttTiktokVideo为空");
            return false;
        }
        if(StringUtils.isEmpty(ttTiktokVideo.getName()) || ttTiktokVideo.getTagId()==null){
            log.info("视频名称或标签ID为空");
            return false;
        }
        String videoKey="";
        try {
            if(ttTiktokVideo.getName().length()>20){
                videoKey= Md5Util.md5(ttTiktokVideo.getName().substring(0,20));
            }else {
                videoKey= Md5Util.md5(ttTiktokVideo.getName());
            }
        } catch (Exception e) {
            log.info("md5出现异常",e);
            return false;
        }
        ttTiktokVideo.setVideoKey(videoKey);
        Example example=new Example(TtTiktokVideo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("videoKey",ttTiktokVideo.getVideoKey());
        criteria.andEqualTo("tagId",ttTiktokVideo.getTagId());
        criteria.andEqualTo("isDelete",0);
        List<TtTiktokVideo> ttTiktokVideos=tiktokVideoMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(ttTiktokVideos)){
            TtTiktokVideo ttTiktokVideoOld=ttTiktokVideos.get(0);
            ttTiktokVideo.setId(ttTiktokVideoOld.getId());
            tiktokVideoMapper.updateByPrimaryKeySelective(ttTiktokVideo);
        }else {
            tiktokVideoMapper.insertSelective(ttTiktokVideo);

        }
        return true;
    }


    public Boolean saveTiktokAccount(TtTiktokAccount ttTiktokAccount){
        if(ttTiktokAccount==null){
            log.info("ttTiktokAccount为空");
            return false;
        }
        if(StringUtils.isEmpty(ttTiktokAccount.getTiktokName()) || ttTiktokAccount.getTagId()==null){
            log.info("TiktokName或标签ID为空");
            return false;
        }
        Example example=new Example(TtTiktokAccount.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tiktokName",ttTiktokAccount.getTiktokName());
        criteria.andEqualTo("tagId",ttTiktokAccount.getTagId());
        criteria.andEqualTo("isDelete",0);
        List<TtTiktokAccount> ttTiktokAccounts=tiktokAccountMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(ttTiktokAccounts)){
            TtTiktokAccount ttTiktokAccountOld=ttTiktokAccounts.get(0);
            ttTiktokAccount.setId(ttTiktokAccountOld.getId());
            tiktokAccountMapper.updateByPrimaryKeySelective(ttTiktokAccount);
        }else {
            tiktokAccountMapper.insertSelective(ttTiktokAccount);
        }
        return true;
    }

    public Boolean saveTaskTrainUserLog(TtTaskTrainUserLog ttTaskTrainUserLog){
        if(ttTaskTrainUserLog==null){
            log.info("ttTiktokAccount为空");
            return false;
        }
        if(StringUtils.isEmpty(ttTaskTrainUserLog.getVideoKey()) || ttTaskTrainUserLog.getUserId()==null){
            log.info("VideoKey或标签UserId为空");
            return false;
        }
        Example example=new Example(TtTaskTrainUserLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",ttTaskTrainUserLog.getUserId());
        criteria.andEqualTo("videoKey",ttTaskTrainUserLog.getVideoKey());
        criteria.andEqualTo("isDelete",0);
        List<TtTaskTrainUserLog> ttTaskTrainUserLogs=ttTaskTrainUserLogMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(ttTaskTrainUserLogs)){
            TtTaskTrainUserLog ttTiktokAccountOld=ttTaskTrainUserLogs.get(0);
            ttTaskTrainUserLog.setId(ttTiktokAccountOld.getId());
            ttTaskTrainUserLogMapper.updateByPrimaryKeySelective(ttTaskTrainUserLog);
        }else {
            ttTaskTrainUserLogMapper.insertSelective(ttTaskTrainUserLog);
        }
        return true;
    }

    public List<TtTaskTrainUserLog> queryTaskTrainUserLog(Integer userId,String videoKey,Integer pageNo,Integer pageSize){
        List<String> videoKeys=new ArrayList<>();
        if(!StringUtils.isEmpty(videoKey)){
            String[] videoArr=videoKey.split(",");
            videoKeys=Arrays.asList(videoArr);
        }
        Integer index=(pageNo-1)*pageSize;
        return ttTaskTrainUserLogMapper.queryTaskTrainUserLog(userId,videoKeys,index,pageSize);
    }

}
