package com.elin.stocksim_back.controller.advice;

import com.elin.stocksim_back.exception.DuplicatedValueException;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.exception.UserAlreadyAuthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalControllerAdvice {
    //    security에서 지원 안하기 때문에 커스텀 사용
    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
//    이미 인증된 사용자
    public ResponseEntity<List<FieldError>> notFoundException(UserAlreadyAuthenticatedException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(NotFoundValueException.class)
//    찾을 수 없음
    public ResponseEntity<List<FieldError>> notFoundException(NotFoundValueException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(DuplicatedValueException.class)
//    중복 데이터
    public ResponseEntity<List<FieldError>> duplicatedException(DuplicatedValueException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    //    인증 하지 않은 사용자
    public ResponseEntity<String> authenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
//    토큰 유효성 검사 또는 인증 시간 초과하는 경우 발생하는 예외
    public ResponseEntity<String> insufficientAuthenticationException(InsufficientAuthenticationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
//    사용자를 조회할 때, 해당 사용자가 존재하지 않을 경우 발생하는 예외
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
//    로그인 시 잘못된 자격 증명(아이디 또는 비밀번호가 틀림)으로 인해 발생하는 예외
    public ResponseEntity<String> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
//    계정이 비활성화된 상태일 때 발생하는 예외
    public ResponseEntity<String> disabledException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}