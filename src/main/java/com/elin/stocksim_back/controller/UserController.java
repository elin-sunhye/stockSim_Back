package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;


}
