package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    //    회원가입
    public int save(User user) {
        return userMapper.save(user);
    }

    //    userId로 user 단건 조회
    public Optional<User> getUserByUserId(int userId) {
        return Optional.ofNullable(userMapper.getUserByUserId(userId));
    }

    //    email로 user 단건 조회
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userMapper.getUserByEmail(email));
    }

    //    email 인증 시 accountVerified 업데이트
    public void updateAccountVerified(String email) {
        userMapper.updateAccountVerifiedByEmail(email);
    }

}
