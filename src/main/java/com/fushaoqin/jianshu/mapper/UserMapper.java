package com.fushaoqin.jianshu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fushaoqin.jianshu.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
