package com.elin.stocksim_back.controller;

import com.elin.stocksim_back.dto.request.redis.ReqRedisDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * redis set, get controller
 */

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // redis 저장소

    @Autowired
    private ObjectMapper objMapper;

    @PostMapping("/json")
    public ResponseEntity<?> jsonSet(@RequestBody ReqRedisDto reqRedisDto) throws JsonProcessingException {
        String json = objMapper.writeValueAsString(reqRedisDto);
        redisTemplate.opsForValue().set(reqRedisDto.getKey(), json, Duration.ofMinutes(5)); // json 객체 저장
        return ResponseEntity.ok().build();
    }

    @GetMapping("/json/{key}")
    public ResponseEntity<ReqRedisDto> jsonGet(@PathVariable String key) throws JsonProcessingException {
        String value = redisTemplate.opsForValue().get(key).toString();
        ReqRedisDto reqRedisDto = objMapper.readValue(value, ReqRedisDto.class); // 객체 리턴
        return ResponseEntity.ok(reqRedisDto);
    }

    @PostMapping("/{key}/{value}")
    public ResponseEntity<?> set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set("user:" + key + ":name", value, Duration.ofSeconds(60)); // 문자열 저장
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        String value = redisTemplate.opsForValue().get(key).toString(); // 문자열 리턴
        return ResponseEntity.ok(value);
    }
}
