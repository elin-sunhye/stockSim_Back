package com.elin.stocksim_back.dto.request.redis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "redis study DTO")
public class ReqRedisDto {
    @Schema(description = "키", example = "key1")
    private String key;

    @Schema(description = "데이터", example = "value1")
    private String value;

    @Schema(description = "데이터 리스트", example = "[1, 2, 3]")
    private List<String> values;
}
