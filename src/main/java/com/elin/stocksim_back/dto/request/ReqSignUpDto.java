package com.elin.stocksim_back.dto.request;

import lombok.Data;

@Data
public class ReqSignUpDto {
    private String email;
    private String password;
    private String name;
    private String phoneNum;
    private int roleId;
}
