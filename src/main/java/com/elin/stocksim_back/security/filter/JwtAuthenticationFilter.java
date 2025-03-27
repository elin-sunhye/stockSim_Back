package com.elin.stocksim_back.security.filter;

import com.elin.stocksim_back.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        해당 요청으로 들어온 토큰값
        String bearerToken = getAuthorization((HttpServletRequest) servletRequest);

//        유효성 체크
        if(isValidToken(bearerToken)) {
            String accessToken = removeBearer(bearerToken);
            Claims claims = jwtUtil.parseToken(accessToken);

            if(claims != null) {
//                인증절차
                int userId = Integer.parseInt(claims.getId());
                String email = claims.getSubject();
//
//                User user = getUser(userId);
//                setAuthentication(user);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

//    헤더에 있는 토큰 가져오기
    private String getAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

//    토큰 유효성 확인
    private boolean isValidToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }

//    토큰 유효성 확인 후 "Bearer " 텍스트 제거
    private String removeBearer(String bearerToken) {
        return bearerToken.substring(7);
    }
}
