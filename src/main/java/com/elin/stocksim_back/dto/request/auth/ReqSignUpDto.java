package com.elin.stocksim_back.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원가입 요청을 위한 DTO")
public class ReqSignUpDto {
    @Schema(description = "유저 아이디(이메일)", example = "user@example.com")
    private String email;

    @Schema(description = "유저 비밀번호", example = "password123")
    private String password;

    @Schema(description = "유저 이름", example = "홍길동")
    private String name;

    @Schema(description = "유저 전화번호", example = "01012345678")
    private String phoneNum;

    @Schema(description = "유저 전화번호 인증", example = "1")
    private int verifiedPhoneNum;

    @Schema(description = "유저 권한 ID", example = "1", allowableValues = {"1", "2", "3"})
    private int roleId;
}
