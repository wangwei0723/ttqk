package com.haozi.ttqk.service.impl;

import com.haozi.ttqk.mapper.*;
import com.haozi.ttqk.model.*;
import com.haozi.ttqk.service.OperationManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("operationManagementService")
public class OperationManagementServiceImpl implements OperationManagementService {
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

    public Boolean savePhone(TtPhone ttPhone){
        if(ttPhone==null){
            log.info("ttPhone为空");
            return false;
        }
        ttPhoneMapper.insertSelective(ttPhone);
        return true;
    }

    public List<TtPhone> queryPhone(TtPhone ttPhone){
        Example example=new Example(TtPhone.class);
        Example.Criteria criteria = example.createCriteria();
        if(ttPhone!=null){
            if(ttPhone.getPhoneId()!=null){
                criteria.andEqualTo("phoneId",ttPhone.getPhoneId());
            }
            if(ttPhone.getUuid()!=null){
                criteria.andEqualTo("uuid",ttPhone.getUuid());
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

    public TtTag getTagById(Integer tagId){
        Example example=new Example(TtPhone.class);
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

}
