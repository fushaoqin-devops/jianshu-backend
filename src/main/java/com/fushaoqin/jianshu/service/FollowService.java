package com.fushaoqin.jianshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fushaoqin.jianshu.model.Follow;

import java.util.List;

public interface FollowService extends IService<Follow> {

    void follow(Long userId, Long followId);

    List<Long> getFollowList(Long userId);

}
