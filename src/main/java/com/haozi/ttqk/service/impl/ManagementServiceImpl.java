package com.haozi.ttqk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haozi.ttqk.mapper.*;
import com.haozi.ttqk.model.*;
import com.haozi.ttqk.service.ManagementService;
import com.haozi.ttqk.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<TtPhone> queryPhone(TtPhone ttPhone){
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
        List<TtPhone> ttPhones=ttPhoneMapper.selectByExample(example);
        return ttPhones;
    }

    public Boolean saveTiktokUser(TiktokUser tiktokUser){
        if(tiktokUser==null){
            log.info("tiktokUser为空");
            return false;
        }
        tiktokUserMapper.insertSelective(tiktokUser);
        return true;
    }

    public List<TiktokUser> queryTiktokUser(TiktokUser tiktokUser){
        Example example=new Example(TiktokUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(tiktokUser!=null){
            if(tiktokUser.getUserId()!=null){
                criteria.andEqualTo("userId",tiktokUser.getUserId());
            }
            if(tiktokUser.getName()!=null){
                criteria.andEqualTo("name",tiktokUser.getName());
            }
            if(tiktokUser.getTiktokId()!=null){
                criteria.andEqualTo("tiktokId",tiktokUser.getTiktokId());
            }
            if(tiktokUser.getPhone()!=null){
                criteria.andEqualTo("phone",tiktokUser.getPhone());
            }
            if(tiktokUser.getPhoneId()!=null){
                criteria.andEqualTo("phoneId",tiktokUser.getPhoneId());
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
        List<TiktokUser> tiktokUsers=tiktokUserMapper.selectByExample(example);
        return tiktokUsers;
    }


    public Integer saveTag(TtTag ttTag){
        if(ttTag==null){
            log.info("tttTag为空");
            return null;
        }
        tagMapper.insertSelective(ttTag);
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

    public TtTag getTagById(Integer tagId){
        Example example=new Example(TtTag.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andEqualTo("id",tagId);
        List<TtTag> ttTags=tagMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(ttTags)){
            return null;
        }

        return ttTags.get(0);
    }


    public Integer uploadVideo(TtVideo ttVideo){
        if(ttVideo==null){
            log.info("ttVideo为空");
            return null;
        }
        videoMapper.insertSelective(ttVideo);
        return ttVideo.getId();
    }

    public Integer saveComment(TtComment ttComment){
        if(ttComment==null){
            log.info("ttComment为空");
            return null;
        }
        commentMapper.insertSelective(ttComment);
        return ttComment.getId();
    }

    public List<TtComment> getComment(Integer tagId,Integer type,String comment,Integer pageNo,Integer pageSize){
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
        List<TtComment> ttComments=new PageInfo<TtComment>(commentMapper.selectByExample(example)).getList();
        return ttComments;
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

    public List<TtTaskTrainUser> queryTaskTrainUser(TtTaskTrainUser ttTaskTrainUser){
        Example example=new Example(TtTaskTrainUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskTrainUser!=null){
            if(ttTaskTrainUser.getTagId()!=null){
                criteria.andEqualTo("tagId",ttTaskTrainUser.getTagId());
            }
        }
        List<TtTaskTrainUser> taskTrainUsers=ttTaskTrainUserMapper.selectByExample(example);
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

    public List<TtTaskSend> queryTaskSend(TtTaskSend ttTaskSend){
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
        List<TtTaskSend> ttTaskSends=ttTaskSendMapper.selectByExample(example);
        return ttTaskSends;
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

    public List<TtTaskAddFans> queryTaskAddFans(TtTaskAddFans ttTaskAddFans){
        Example example=new Example(TtTaskAddFans.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttTaskAddFans!=null){
            if(ttTaskAddFans.getTagId()!=null){
                criteria.andEqualTo("tagId",ttTaskAddFans.getTagId());
            }
        }
        List<TtTaskAddFans> ttTaskAddFansList=ttTaskAddFansMapper.selectByExample(example);
        return ttTaskAddFansList;
    }

    public List<TtVideo> queryUserUnUploadVideo(Integer userId){
        Example example=new Example(TtVideo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("uploadState",0);
        criteria.andEqualTo("isDelete",0);
        List<TtVideo> ttVideos=videoMapper.selectByExample(example);
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

}
