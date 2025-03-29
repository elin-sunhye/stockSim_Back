package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.dto.request.auth.ReqSignInDto;
import com.elin.stocksim_back.dto.request.auth.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.auth.RespAuthDto;
import com.elin.stocksim_back.dto.response.auth.RespSignUpDto;
import com.elin.stocksim_back.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "권한 관련 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(
            summary = "회원가입",
            description = "유저 등록"
    )
    @PostMapping("/signup")
    public ResponseEntity<RespSignUpDto> signUp(@RequestBody ReqSignUpDto dto) {
        return ResponseEntity.ok().body(authService.signUp(dto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<RespAuthDto> signIn(@RequestBody ReqSignInDto dto) {
        return ResponseEntity.ok().body(authService.signIn(dto));
    }

    @Operation(summary = "refreshToken", description = "accessToken 만료 시 refreshToken 만료 확인 후 newAccessToken 생성")
    @PostMapping("/token/refresh")
    public ResponseEntity<RespAuthDto> refresh(@RequestBody RespAuthDto reqRefreshDto) {
        return ResponseEntity.ok().body(authService.refresh(reqRefreshDto.getRefreshToken()));
    }
}
