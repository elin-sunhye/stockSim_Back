package com.elin.stocksim_back.service;

import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUserId(int userId) {
        return userRepository.getUserByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 사용자 입니다."));
    }


}
