package com.elin.stocksim_back.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private Key key;
    private Long accessTokenExpire;
    private Long refreshTokenExpire;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        /**
         * JwtUtil 생성자
         * 생성될 때마다 토큰 시간 셋팅
         */

        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//        AccessToken 10분 ~ 1시간 (보안이 중요할수록 짧게)
        accessTokenExpire = 1000l * 60 * 30; // 30분
//        RefreshToken 7일 ~ 90일 (보안이 중요할수록 짧게)
        refreshTokenExpire = 1000l * 60 * 60 * 24 * 7; // 7일
    }

//    토큰 생성
    public String generateToken (String userId, String email, boolean isRefreshToken) {
        return Jwts.builder()
                .setId(userId)
                .setSubject(email)
                .setExpiration(
                        new Date(System.currentTimeMillis() + (isRefreshToken ? refreshTokenExpire : accessTokenExpire))
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

//    프론트에서 보내준 토큰 파싱
    public Claims parseToken(String token) {
        Claims claims = null;

        try{
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }
}
