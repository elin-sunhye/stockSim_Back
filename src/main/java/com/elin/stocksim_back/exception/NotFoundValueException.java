package com.elin.stocksim_back.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class NotFoundValueException extends RuntimeException {
    private List<FieldError> fieldErrors;

    public NotFoundValueException(List<FieldError> fieldErrors) {
        super("찾을수 없음");
        this.fieldErrors = fieldErrors;
    }
}
