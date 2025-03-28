package com.elin.stocksim_back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private int userRoleId;
    private int userId; // FK - User
    private int roleId; // FK - Role

    private Role role;
}
