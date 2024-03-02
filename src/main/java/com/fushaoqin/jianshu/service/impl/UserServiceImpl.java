package com.fushaoqin.jianshu.service.impl;

import ch.qos.logback.core.joran.sanity.Pair;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushaoqin.jianshu.common.CustomException;
import com.fushaoqin.jianshu.common.JwtHelper;
import com.fushaoqin.jianshu.mapper.UserMapper;
import com.fushaoqin.jianshu.model.Dto.UserInfoDto;
import com.fushaoqin.jianshu.model.Dto.UserLoginDto;
import com.fushaoqin.jianshu.model.Dto.UserRegistrationDto;
import com.fushaoqin.jianshu.model.User;
import com.fushaoqin.jianshu.service.ArticleService;
import com.fushaoqin.jianshu.service.FollowService;
import com.fushaoqin.jianshu.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public String login(UserLoginDto userLoginDto) {
        String account = userLoginDto.getAccount();
        String password = userLoginDto.getPassword();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            throw new CustomException(201, "用户名或密码不能为空");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, account).or().eq(User::getPhone, account);
        User user = getOne(queryWrapper);

        if (user == null) {
            throw new CustomException(201, "用户不存在");
        }

        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new CustomException(201, "密码错误");
        }

        if (user.getStatus() == 0) {
            throw new CustomException(201, "用户已被禁用");
        }

        return JwtHelper.createToken(user.getId(), user.getUsername(), user.getName());
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        String password = userRegistrationDto.getPassword();
        String name = userRegistrationDto.getName();
        String phone = userRegistrationDto.getPhone();

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)) {
            throw new CustomException(201, "用户名、密码、姓名、手机号不能为空");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name).or().eq(User::getPhone, phone);
        User user = getOne(queryWrapper);

        if (user != null) {
            throw new CustomException(201, "用户名或手机号已存在");
        }

        User newUser = new User();
        newUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        UUID uuid = UUID.randomUUID();
        newUser.setUsername(uuid.toString() + "@gmail.com");
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setStatus(1);
        save(newUser);
    }

    @Override
    public List<UserInfoDto> getAllUserInfo() {
        List<User> users = list();
        List<UserInfoDto> userInfoDtos = users.stream().map(user -> {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userInfoDto);
            return userInfoDto;
        }).toList();
        return userInfoDtos;
    }

}
