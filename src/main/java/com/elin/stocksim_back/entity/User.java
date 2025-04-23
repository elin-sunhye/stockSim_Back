package com.elin.stocksim_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "유저")
public class User {
    @Schema(description = "유저 고유 번호", example = "1")
    private int userId;

    @Schema(description = "유저 아이디(이메일)", example = "user@example.com")
    private String email;

    @Schema(description = "유저 비밀번호", example = "password123")
    private String password;

    @Schema(description = "유저 이름", example = "홍길동")
    private String name;

    @Schema(description = "유저 전화번호", example = "01022223333")
    private String phoneNum;

    @Schema(description = "유저 가상 포인트 잔액", example = "30000")
    private int balance;

    @Schema(description = "계정 토큰 만료 여부", example = "0")
    private int accountExpired;

    @Schema(description = "계정 인증 여부", example = "0")
    private int accountVerified;

    @Schema(description = "유저 권한 리스트", example = "{userRoleId: 1, userId: 1, roleId: 1}")
    private List<UserRole> userRoles;
}
