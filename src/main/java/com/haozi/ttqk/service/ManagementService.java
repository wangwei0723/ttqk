package com.haozi.ttqk.service;

import com.haozi.ttqk.model.*;
import com.haozi.ttqk.vo.PhoneResponseVo;
import com.haozi.ttqk.vo.VideoResponseVo;

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
    PhoneResponseVo queryPhone(TtPhone ttPhone, Integer pageNo, Integer pageSize);

    /**
     * 保存tiktok用户
     * @param tiktokUser
     * @return
     */
    Boolean saveTiktokUser(TiktokUser tiktokUser);

    /**
     * 校验用户是否存在
     * @param userId
     * @param name
     * @param tiktokId
     * @return
     */
    Integer checkIsUserExist(String userId, String name, String tiktokId);

    /**
     * 查询tiktok用户
     * @param tiktokUser
     * @return
     */
    List<TiktokUser> queryTiktokUser(TiktokUser tiktokUser,Integer pageNo,Integer pageSize);

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
    List<TtComment> getComment(Integer tagId,Integer type,String comment,Integer pageNo,Integer pageSize);

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
    List<TtTaskTrainUser> queryTaskTrainUser(TtTaskTrainUser ttTaskTrainUser,Integer pageNo,Integer pageSize);

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
    List<TtTaskSend> queryTaskSend(TtTaskSend ttTaskSend,Integer pageNo,Integer pageSize);

    /**
     * 保存TaskSendLog
     * @param ttTaskSendLog
     * @return
     */
    Boolean saveTaskSendLog(TtTaskSendLog ttTaskSendLog);

    /**
     * 查询TaskSendLog
     * @param ttTaskSendLog
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<TtTaskSendLog> queryTaskSendLog(TtTaskSendLog ttTaskSendLog,Integer pageNo,Integer pageSize);

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
    List<TtTaskAddFans> queryTaskAddFans(TtTaskAddFans ttTaskAddFans,Integer pageNo,Integer pageSize);

    /**
     * 获取用户所有未上传的视频
     * @param userId
     * @return
     */
    List<TtVideo> queryUserUnUploadVideo(Integer userId,Integer pageNo,Integer pageSize);

    /**
     * 获取视频列表
     * @param ttVideo
     * @param pageNo
     * @param pageSize
     * @return
     */
    VideoResponseVo queryVideo(TtVideo ttVideo, Integer pageNo, Integer pageSize);

    /**
     * 更新视频上传状态
     * @param id
     * @return
     */
    Boolean updateVideoUploadState(Integer id);

    /**
     * 保存ttTiktokVideo
     * @param ttTiktokVideo
     * @return
     */
    Boolean saveTiktokVideo(TtTiktokVideo ttTiktokVideo);

    /**
     * 保存TiktokAccount
     * @param ttTiktokAccount
     * @return
     */
    Boolean saveTiktokAccount(TtTiktokAccount ttTiktokAccount);

    /**
     * 保存askTrainUserLog
     * @param ttTaskTrainUserLog
     * @return
     */
    Boolean saveTaskTrainUserLog(TtTaskTrainUserLog ttTaskTrainUserLog);

    /**
     * 查询askTrainUserLog
     * @param userId
     * @param videoKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<TtTaskTrainUserLog> queryTaskTrainUserLog(Integer userId,String videoKey,Integer pageNo,Integer pageSize);

}
