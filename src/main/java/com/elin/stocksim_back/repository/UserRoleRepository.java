package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.entity.UserRole;
import com.elin.stocksim_back.mappers.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public int save(UserRole userRole) {
        return userRoleMapper.save(userRole);
    }
}
