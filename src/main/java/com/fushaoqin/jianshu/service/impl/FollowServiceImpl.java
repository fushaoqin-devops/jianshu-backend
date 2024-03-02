package com.fushaoqin.jianshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushaoqin.jianshu.mapper.FollowMapper;
import com.fushaoqin.jianshu.model.Follow;
import com.fushaoqin.jianshu.service.FollowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {


    @Override
    public void follow(Long userId, Long followId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowId(followId);
        save(follow);
    }

    @Override
    public List<Long> getFollowList(Long userId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId);
        List<Follow> follows = list(wrapper);
        if (follows == null) {
            return null;
        }
        return follows.stream().map(Follow::getFollowId).toList();
    }

}
