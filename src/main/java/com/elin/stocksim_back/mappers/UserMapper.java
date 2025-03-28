package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //    회원가입
    int save(User user);

    //    userId로 user 단건 조회
    User getUserByUserId(@Param("userId") int userId);

    //    email로 user 단건 조회
    User getUserByEmail(@Param("email") String email);
}
