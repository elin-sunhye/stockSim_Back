package com.elin.stocksim_back.controller;

import com.elin.stocksim_back.dto.request.redis.ReqRedisDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Tag(name = "redis", description = "Redis 관련 API")
@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // redis 저장소

    @Autowired
    private ObjectMapper objMapper;

    @Operation(summary = "Redis 등록")
    @PostMapping("/json")
    public ResponseEntity<?> jsonSet(@RequestBody ReqRedisDto reqRedisDto) throws JsonProcessingException {
        String json = objMapper.writeValueAsString(reqRedisDto);
        redisTemplate.opsForValue().set(reqRedisDto.getKey(), json, Duration.ofMinutes(5)); // json 객체 저장
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Redis 조회")
    @GetMapping("/json/{key}")
    public ResponseEntity<ReqRedisDto> jsonGet(@PathVariable String key) throws JsonProcessingException {
        String value = redisTemplate.opsForValue().get(key).toString();
        ReqRedisDto reqRedisDto = objMapper.readValue(value, ReqRedisDto.class); // 객체 리턴
        return ResponseEntity.ok(reqRedisDto);
    }

    @Operation(summary = "이미 등록된 Redis 데이터 덮어쓰기")
    @PostMapping("/{key}/{value}")
    public ResponseEntity<?> set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set("user:" + key + ":name", value, Duration.ofSeconds(60)); // 문자열 저장
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Redis 단건 조회")
    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        String value = redisTemplate.opsForValue().get(key).toString(); // 문자열 리턴
        return ResponseEntity.ok(value);
    }
}
