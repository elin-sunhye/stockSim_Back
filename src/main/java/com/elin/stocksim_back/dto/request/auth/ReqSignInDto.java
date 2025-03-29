package com.elin.stocksim_back.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 요청을 위한 DTO")
public class ReqSignInDto {
    @Schema(description = "유저 아이디(이메일)", example = "user@example.com")
    private String email;

    @Schema(description = "유저 비밀번호", example = "password123")
    private String password;
}
