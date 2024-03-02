package com.fushaoqin.jianshu.common;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    private static long tokenExpireTime = 30 * 60 * 1000;
    // for development
    private static String tokenSignKey = "LoobNjFjmzciMhkVE5uv097GubrdUOzISnCaEyOC9op+2KbgcHbBO9ErVOvUfXxrh0azvXwZf7IBcwvSWrPsNA==";

    public static String createToken(Long userId, String username, String name) {
        String token = Jwts.builder()
                .setSubject("AUTH_USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .claim("userId", userId)
                .claim("username", username)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token) || token.equals("null")) return null;

        return Long.valueOf(Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userId")
                .toString());
    }

    public static String getUsername(String token) {
        if (StringUtils.isEmpty(token) || token.equals("null")) return "";

        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .get("username")
                .toString();
    }

    public static String getName(String token) {
        if (StringUtils.isEmpty(token) || token.equals("null")) return "";

        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .get("name")
                .toString();
    }

    public static boolean verifyToken(String token) {
        if (StringUtils.isEmpty(token) || token.equals("null")) return false;

        try {
            Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
