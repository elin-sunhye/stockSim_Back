package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.dto.request.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.RespSignUpDto;
import com.elin.stocksim_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    //    회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<RespSignUpDto> signUp(@RequestBody ReqSignUpDto dto) {
        return ResponseEntity.ok().body(userService.signUp(dto));
    }

//    로그인
}
