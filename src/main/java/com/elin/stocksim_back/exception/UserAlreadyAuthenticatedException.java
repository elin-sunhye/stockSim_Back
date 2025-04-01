package com.elin.stocksim_back.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class UserAlreadyAuthenticatedException extends RuntimeException {
    private List<FieldError> fieldErrors;

    public UserAlreadyAuthenticatedException(List<FieldError> fieldErrors) {
        super("이미 인증된 사용자");
        this.fieldErrors = fieldErrors;
    }
}
