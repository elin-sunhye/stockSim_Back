package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    //    roleId로 role 단건 조회
    Role getRoleByRoleId(@Param("roleId") int roleId);
}
