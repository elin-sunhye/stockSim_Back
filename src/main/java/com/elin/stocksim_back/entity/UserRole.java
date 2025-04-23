package com.elin.stocksim_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "유저 권한")
public class UserRole {
    private int userRoleId;
    private int userId; // FK - User
    private int roleId; // FK - Role

    private Role role;
}
