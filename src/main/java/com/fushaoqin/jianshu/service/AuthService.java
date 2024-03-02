package com.fushaoqin.jianshu.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void setCookie(HttpServletResponse response, String token);

    void removeCookie(HttpServletResponse response);

    String verifyCookie(HttpServletRequest request);

    Long getUserId(HttpServletRequest request);

}
