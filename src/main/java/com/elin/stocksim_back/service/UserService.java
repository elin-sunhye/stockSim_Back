package com.elin.stocksim_back.service;

import com.elin.stocksim_back.dto.request.ReqSignInDto;
import com.elin.stocksim_back.dto.request.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.RespSignUpDto;
import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.entity.UserRole;
import com.elin.stocksim_back.exception.DuplicatedValueException;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.repository.RoleRepository;
import com.elin.stocksim_back.repository.UserRepository;
import com.elin.stocksim_back.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //    이메일 확인
    private boolean duplicateEmail(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    //    roleId 확인
    private boolean validRoleId(int roleId) {
        return roleRepository.getRoleByRoleId(roleId).isPresent();
    }


    //    회원가입
    @Transactional(rollbackFor = Exception.class)
    public RespSignUpDto signUp(ReqSignUpDto dto) {
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

//        새 유저 빌드해서 저장
        User newUser = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .build();

        userRepository.save(newUser);

//        새 유저의 롤을 유저롤 데이블에 추가
        UserRole userRole = UserRole.builder()
                .userId(newUser.getUserId())
                .roleId(dto.getRoleId())
                .build();
        userRoleRepository.save(userRole);

        //        휴대폰 인증 여부 확인

        RespSignUpDto respSignUpDto = new RespSignUpDto();
        respSignUpDto.setUserId(newUser.getUserId());

        return respSignUpDto;
    }

    //    로그인
    public String signIn(ReqSignInDto dto) {
//        user 이메일 확인 오케이 되면  200 아니면 예외
        if (!duplicateEmail(dto.getEmail())) {
            throw new NotFoundValueException(List.of(FieldError.builder()
                    .field("email")
                    .message("존재하지 않은 사용자입니다.")
                    .build()));
        }


//        password 일치 확인 오케이면 200 아니면 예외
//        userRepository.getUserByEmail(email)
        return "";
    }
}
