package com.elin.stocksim_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 현재 보유 주식")
public class Portfolio {
    @Schema(description = "보유 주식 고유 아이디", example = "1")
    private int portfolioId;

    @Schema(description = "유저 고유 아이디", example = "1")
    private int userId;

    @Schema(description = "주식 고유 아이디", example = "1")
    private int stockId;

    @Schema(description = "주식 수량", example = "1")
    private int quantity;
}
