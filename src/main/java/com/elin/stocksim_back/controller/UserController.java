package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "권한 관련 API")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
}
