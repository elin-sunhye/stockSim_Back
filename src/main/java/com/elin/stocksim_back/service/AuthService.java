package com.elin.stocksim_back.service;

import com.elin.stocksim_back.dto.request.auth.ReqSignInDto;
import com.elin.stocksim_back.dto.request.auth.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.auth.RespAuthDto;
import com.elin.stocksim_back.dto.response.auth.RespSignUpDto;
import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.entity.UserRole;
import com.elin.stocksim_back.exception.DuplicatedValueException;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.repository.RoleRepository;
import com.elin.stocksim_back.repository.UserRepository;
import com.elin.stocksim_back.repository.UserRoleRepository;
import com.elin.stocksim_back.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTokenService redisTokenService;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    //    이메일 확인
    public boolean duplicateEmail(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    //    roleId 확인
    public boolean validRoleId(int roleId) {
        return roleRepository.getRoleByRoleId(roleId).isPresent();
    }

    //    회원가입
    @Transactional(rollbackFor = Exception.class)
    public RespSignUpDto signUp(ReqSignUpDto dto) throws Exception {
//        저장 전 중복 확인
        if (duplicateEmail(dto.getEmail())) {
            throw new DuplicatedValueException(List.of(FieldError.builder()
                    .field("email")
                    .message("이미 존재하는 사용자입니다.")
                    .build()));
        }

//        저장 전 유효한 roleId인지 확인
        if (!validRoleId(dto.getRoleId())) {
            throw new NotFoundValueException(List.of(FieldError.builder()
                    .field("roleId")
                    .message("유효하지 않은 권한입니다.")
                    .build()));
        }

//        저장 전 인증확인
//        if (dto.getVerifiedPhoneNum() == 0) {
//            throw new AuthenticationCredentialsNotFoundException("인증되지 않은 사용자 입니다.");
//        }

//        새 유저 빌드해서 저장
        User newUser = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phoneNum(dto.getPhoneNum())
                .build();
        userRepository.save(newUser);

//        새 유저의 롤을 유저롤 데이블에 추가
        UserRole userRole = UserRole.builder()
                .userId(newUser.getUserId())
                .roleId(dto.getRoleId())
                .build();
        userRoleRepository.save(userRole);

        RespSignUpDto respSignUpDto = new RespSignUpDto();
        respSignUpDto.setUserId(newUser.getUserId());

        return respSignUpDto;
    }

    //    로그인
    public RespAuthDto signIn(ReqSignInDto dto) {
//        유저 찾기
        System.out.println(dto);
        User foundUser = userRepository.getUserByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("email: 사용자 정보를 확인하세요."));

//        password 일치하지 않으면
        if (!passwordEncoder.matches(dto.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("password: 사용자 정보를 확인하세요.");
        }

//        다 오케이면 토큰 생성
        String accessToken = jwtUtil.generateToken(Integer.toString(foundUser.getUserId()), foundUser.getEmail(), true);
        String refreshToken = jwtUtil.generateToken(Integer.toString(foundUser.getUserId()), foundUser.getEmail(), false);

//        redis에 token 저장
//        기존 존재 시 덮어쓰기
        redisTokenService.setAccess(dto.getEmail(), accessToken, Duration.ofMinutes(60));
        redisTokenService.setRefresh(dto.getEmail(), refreshToken, Duration.ofDays(7));

        return RespAuthDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //    refreshToken
    public RespAuthDto refresh(String refreshToken) {
//        파싱
        Claims claims = jwtUtil.parseToken(refreshToken);

//        없으면?
        if (claims == null) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }

        String userId = claims.getId();
        String email = claims.getSubject();
        String redisRefresh = redisTokenService.getRefreshToken(email);

//        없거나 다르면?
        if (redisRefresh == null || !redisRefresh.equals(refreshToken)) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }

//        새로운 accessToken 생성
        String newAccessToken = jwtUtil.generateToken(userId, email, false);

        return RespAuthDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
