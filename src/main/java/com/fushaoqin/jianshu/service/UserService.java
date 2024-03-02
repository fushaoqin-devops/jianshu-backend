package com.fushaoqin.jianshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fushaoqin.jianshu.model.Dto.UserInfoDto;
import com.fushaoqin.jianshu.model.Dto.UserLoginDto;
import com.fushaoqin.jianshu.model.Dto.UserRegistrationDto;
import com.fushaoqin.jianshu.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService extends IService<User> {

    String login(UserLoginDto userLoginDto);

    void register(UserRegistrationDto userRegistrationDto);

    List<UserInfoDto> getAllUserInfo();
}
