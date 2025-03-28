package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    //    유저롤 저장
    int save(UserRole userRole);

}
