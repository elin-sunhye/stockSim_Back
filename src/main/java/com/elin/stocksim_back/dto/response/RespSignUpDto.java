package com.elin.stocksim_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원가입 응답을 위한 DTO")
public class RespSignUpDto {
    @Schema(description = "유저 고유 번호", example = "1")
    private int userId;
}
