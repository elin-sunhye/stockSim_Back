package com.elin.stocksim_back.controller.advice;

import com.elin.stocksim_back.exception.DuplicatedValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(DuplicatedValueException.class)
    //    security에서 duplicateException은 지원 안하기 때문에 커스텀 DuplicatedValueException을 사용
    public ResponseEntity<?> duplicatedException(DuplicatedValueException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
//    사용자를 조회할 때, 해당 사용자가 존재하지 않을 경우 발생하는 예외
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
//    로그인 시 잘못된 자격 증명(아이디 또는 비밀번호가 틀림)으로 인해 발생하는 예외
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
//    계정이 비활성화된 상태일 때 발생하는 예외
    public ResponseEntity<?> disabledException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}