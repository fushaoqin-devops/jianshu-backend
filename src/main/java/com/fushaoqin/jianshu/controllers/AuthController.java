package com.fushaoqin.jianshu.controllers;

import com.fushaoqin.jianshu.common.CustomException;
import com.fushaoqin.jianshu.common.Result;
import com.fushaoqin.jianshu.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/verify")
    public Result verify(HttpServletRequest request) {
        String name = authService.verifyCookie(request);
        if (name == null) {
           throw new CustomException(401, "未登录");
        }
        return Result.ok(name);
    }

}
