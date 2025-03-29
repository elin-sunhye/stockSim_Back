package com.elin.stocksim_back.service;

import com.elin.stocksim_back.dto.request.ReqSignInDto;
import com.elin.stocksim_back.dto.request.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.RespSignUpDto;
import com.elin.stocksim_back.entity.Role;
import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.entity.UserRole;
import com.elin.stocksim_back.exception.DuplicatedValueException;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.repository.RoleRepository;
import com.elin.stocksim_back.repository.UserRepository;
import com.elin.stocksim_back.repository.UserRoleRepository;
import com.elin.stocksim_back.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


}
