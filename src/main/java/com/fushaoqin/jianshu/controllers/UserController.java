package com.fushaoqin.jianshu.controllers;

import com.fushaoqin.jianshu.common.Result;
import com.fushaoqin.jianshu.model.Dto.UserInfoDto;
import com.fushaoqin.jianshu.model.Dto.UserLoginDto;
import com.fushaoqin.jianshu.model.Dto.UserRegistrationDto;
import com.fushaoqin.jianshu.service.ArticleService;
import com.fushaoqin.jianshu.service.AuthService;
import com.fushaoqin.jianshu.service.FollowService;
import com.fushaoqin.jianshu.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FollowService followService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        // verify user credentials
        String token = userService.login(userLoginDto);
        // set cookie
        authService.setCookie(response, token);
        return Result.ok(null);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);
        return Result.ok(null);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletResponse response) {
        // invalidate cookie
        authService.removeCookie(response);
        return Result.ok(null);
    }

    @GetMapping("/userInfo/{page}/{limit}")
    public Result userInfo(@PathVariable long page,
                           @PathVariable long limit,
                           HttpServletRequest request) {
        // TODO: refactor to service
        List<UserInfoDto> list = userService.getAllUserInfo();
        list.stream().forEach(userInfoDto -> {
            userInfoDto.setWordCount(articleService.getWordCount(userInfoDto.getId()));
        });

        Long userId = authService.getUserId(request);
        if (userId != null) {
            // handle follow status
            List<Long> followList = followService.getFollowList(userId);
            list.stream().forEach(userInfoDto -> {
                userInfoDto.setFollowed(followList.contains(userInfoDto.getId()));
            });
        }

        // TODO: use MyBatis Pagination
        int size = list.size();
        list = list.subList((int) ((page - 1) * limit), Math.min((int) (page * limit), size));
        Map result = new HashMap();
        result.put("records", list);
        result.put("total", size);
        return Result.ok(result);
    }

    @PostMapping("/follow/{id}")
    public Result follow(@PathVariable("id") Long id, HttpServletRequest request) {
        Long userId = authService.getUserId(request);
        followService.follow(userId, id);
        return Result.ok(null);
    }

}
