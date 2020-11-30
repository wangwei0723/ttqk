package com.haozi.ttqk.service;

import com.haozi.ttqk.model.TiktokUser;
import com.haozi.ttqk.model.TtPhone;
import com.haozi.ttqk.model.TtTag;

import java.util.List;

public interface OperationManagementService {
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
     * 根据标签ID查询标签
     * @param tagId
     * @return
     */
    TtTag getTagById(Integer tagId);

}
