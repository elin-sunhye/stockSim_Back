package com.elin.stocksim_back.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.SubmissionPublisher;

@Component
public class JwtUtil {
    private Key key;
    private Long accessTokenExpire;
    private Long refreshTokenExpire;
    private Long mailTokenExpire;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        /**
         * JwtUtil 생성자
         * 생성될 때마다 토큰 시간 셋팅
         */

        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

//        AccessToken 10분 ~ 1시간 (보안이 중요할수록 짧게)
        accessTokenExpire = 1000l * 60 * 60; // 60분
//        RefreshToken 7일 ~ 90일 (보안이 중요할수록 짧게)
        refreshTokenExpire = 1000l * 60 * 60 * 24 * 7; // 7일
//        MailToken 7일 ~ 90일 (보안이 중요할수록 짧게)
        mailTokenExpire = 1000l * 60 * 10; // 10분
    }

    //    토큰 생성
    public String generateToken(String userId, String email, String tokenName) {
        return Jwts.builder()
                .setId(userId)
                .setSubject(email)
                .setExpiration(
                        new Date(new Date().getTime() + (tokenName.equals("accessTokenExpire") ? accessTokenExpire : tokenName.equals("refreshTokenExpire") ? refreshTokenExpire : mailTokenExpire))
//                        new Date(System.currentTimeMillis() + (tokenName.equals("accessTokenExpire") ? accessTokenExpire : tokenName.equals("refreshTokenExpire") ? refreshTokenExpire : mailTokenExpire))
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //    프론트에서 보내준 토큰 파싱
    public Claims parseToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            System.out.println("JWT 만료됨: {}" + e.getMessage());
        } catch (JwtException e) {
            System.out.println("JWT 파싱 오류: {}" + e.getMessage());
        } catch (Exception e) {
            System.out.println("알 수 없는 오류: {}" + e.getMessage() + e);
        }

        return claims;
    }
}
