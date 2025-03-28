package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.entity.Role;
import com.elin.stocksim_back.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepository {
    @Autowired
    private RoleMapper roleMapper;

    public Optional<Role> getRoleByRoleId(int roleId) {
        return Optional.ofNullable(roleMapper.getRoleByRoleId(roleId));
    }
}
