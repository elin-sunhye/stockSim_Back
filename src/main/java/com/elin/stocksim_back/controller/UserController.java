package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.dto.request.ReqSignInDto;
import com.elin.stocksim_back.dto.request.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.RespSignUpDto;
import com.elin.stocksim_back.service.UserService;
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
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "회원가입",
            description = "유저 등록"
    )
    @PostMapping("/auth/signup")
    public ResponseEntity<RespSignUpDto> signUp(@RequestBody ReqSignUpDto dto) {
        return ResponseEntity.ok().body(userService.signUp(dto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/auth/signup")
    public ResponseEntity<String> signIn(@RequestBody ReqSignInDto dto) {
        return ResponseEntity.ok().body(userService.signIn(dto));
    }
}
