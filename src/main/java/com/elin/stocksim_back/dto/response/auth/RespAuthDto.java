package com.elin.stocksim_back.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "권한 응답을 위한 DTO")
public class RespAuthDto {
    @Schema(description = "accessToken")
    private String accessToken;

    @Schema(description = "refreshToken")
    private String refreshToken;
}
