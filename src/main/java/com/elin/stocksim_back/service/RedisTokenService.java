package com.elin.stocksim_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisTokenService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setRefresh(String email, String token, Duration duration) {
        redisTemplate.opsForValue().set("refresh:" + email, token, duration);
    }

    public void setAccess(String email, String token, Duration duration) {
        redisTemplate.opsForValue().set("access:" + email, token, duration);
    }

    public String getRefreshToken(String email) {
        return (String) redisTemplate.opsForValue().get("refresh:" + email);
    }
}
