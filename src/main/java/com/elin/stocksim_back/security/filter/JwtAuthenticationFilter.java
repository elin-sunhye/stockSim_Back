package com.elin.stocksim_back.security.filter;

import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.repository.UserRepository;
import com.elin.stocksim_back.security.jwt.JwtUtil;
import com.elin.stocksim_back.security.principal.PrincipalUser;
import com.elin.stocksim_back.service.RedisTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 헤더에 받아온 토큰 파싱해서 user_tb에 있는 user_id와 동일한 정보가 있는지 확인 후 Principal User에 user를 빌드해서 set
 */

@Component
public class JwtAuthenticationFilter implements Filter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTokenService redisTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        헤더 토큰값
        String bearerToken = getAuthorization((HttpServletRequest) servletRequest);

//        유효성 체크
        if (isValidToken(bearerToken)) {
//            Bearer 텍스트 제거
            String accessToken = removeBearer(bearerToken);

//            파싱
            Claims claims = jwtUtil.parseToken(accessToken);


            if (claims == null) {
                throw new JwtException("유효하지 않은 토큰입니다.");
            }

//                인증절차
//                파싱한 토큰의 정보에서 인증에 필요한 정보 get
            int userId = Integer.parseInt(claims.getId());
            String email = claims.getSubject();

//                파싱한 토큰에 있던 정보로 유저 찾기
            User foundUser = getUser(userId);

//                찾은 유저 principalUser에 build하고, 인증 사용자 권한 설정 및 정보 저장
            setAuthentication(foundUser);
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

    //    user_tb에 있는 user_id와 동일한지 확인
    private User getUser(int userId) {
        return userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundValueException(List.of(FieldError.builder()
                .field("userId")
                .message("유효하지 않은 사용자입니다.")
                .build())));
    }

    //    찾은 유저 principalUser에 build
    private void setAuthentication(User foundUser) {
//        foundUser가 없으면
        if (foundUser == null) {
            return;
        }

//        foundUser가 있으면 새로운 principalUser
        PrincipalUser principalUser = new PrincipalUser(foundUser);

//        인증된 사용자 정보(아이디, 권한) 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalUser,
                null,
                principalUser.getAuthorities()
        );

//        현재 사용자의 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}