package com.fushaoqin.jianshu.service.impl;

import com.fushaoqin.jianshu.common.JwtHelper;
import com.fushaoqin.jianshu.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void setCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        // jwtCookie.setSecure(true); // disabled for development
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(jwtCookie);
    }

    @Override
    public void removeCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwtToken", null); // Overwrite and clear the JWT cookie
        jwtCookie.setHttpOnly(true);
        // jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Makes the cookie expire immediately
        response.addCookie(jwtCookie);
    }

    @Override
    public String verifyCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    Boolean verified = JwtHelper.verifyToken(token);
                    if (!verified) return null;
                    return JwtHelper.getName(token);
                }
            }
        }
        return null;
    }

    @Override
    public Long getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    return JwtHelper.getUserId(token);
                }
            }
        }
        return null;
    }

}
