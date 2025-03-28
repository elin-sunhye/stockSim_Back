package com.elin.stocksim_back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String email;
    private String password;
    private String name;
    private String phoneNum;
    private int balance;
    private int accountExpired; // 1 true만료 0 false - 토큰 시간 만료되면 1
    private int accountVerified; // 1 true활성화 0 false - 휴대폰 인증 하면 1
    private int roleId; // FK - Role

    private List<UserRole> userRoles;
}
