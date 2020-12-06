package com.haozi.ttqk.service;

import com.haozi.ttqk.model.*;

import java.util.List;
import java.util.Map;

public interface ManagementService {
    /**
     * 保存手机
     * @param ttPhone
     * @return
     */
    Boolean savePhone(TtPhone ttPhone);

    /**
     * 查询手机
     * @param ttPhone
     * @return
     */
    List<TtPhone> queryPhone(TtPhone ttPhone);

    /**
     * 保存tiktok用户
     * @param tiktokUser
     * @return
     */
    Boolean saveTiktokUser(TiktokUser tiktokUser);

    /**
     * 查询tiktok用户
     * @param tiktokUser
     * @return
     */
    List<TiktokUser> queryTiktokUser(TiktokUser tiktokUser);

    /**
     * 保存标签
     * @param ttTag
     * @return
     */
    Integer saveTag(TtTag ttTag);

    /**
     * 获取所有标签
     * @return
     */
    List<TtTag> getAllTag();

    /**
     * 获取所有标签map
     * @return
     */
    Map<Integer,String> getTagMap();

    /**
     * 根据标签ID查询标签
     * @param tagId
     * @return
     */
    TtTag getTagById(Integer tagId);

    /**
     * 上传视频
     * @param ttVideo
     * @return
     */
    Integer uploadVideo(TtVideo ttVideo);

    /**
     * 保存评论
     * @param ttComment
     * @return
     */
    Integer saveComment(TtComment ttComment);

    /**
     * 获取所有评论
     * @return
     */
    List<TtComment> getAllComment();

    /**
     * 获取评论map
     * @return
     */
    Map<Integer,String> getCommentMap();

    /**
     * 保存养号任务
     * @param ttTaskTrainUser
     * @return
     */
    Boolean saveTaskTrainUser(TtTaskTrainUser ttTaskTrainUser);

    /**
     * 查询养号任务
     * @param ttTaskTrainUser
     * @return
     */
    List<TtTaskTrainUser> queryTaskTrainUser(TtTaskTrainUser ttTaskTrainUser);

    /**
     * 保存发送任务
     * @param ttTaskSend
     * @return
     */
    Boolean saveTaskSend(TtTaskSend ttTaskSend);

    /**
     * 查询发送任务
     * @param ttTaskSend
     * @return
     */
    List<TtTaskSend> queryTaskSend(TtTaskSend ttTaskSend);

    /**
     * 保存添加粉丝
     * @param ttTaskAddFans
     * @return
     */
    Boolean saveTaskAddFans(TtTaskAddFans ttTaskAddFans);

    /**
     * 查询添加粉丝
     * @param ttTaskAddFans
     * @return
     */
    List<TtTaskAddFans> queryTaskAddFans(TtTaskAddFans ttTaskAddFans);

}
