package com.elin.stocksim_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 주식 거래 이력")
public class Transactions {
    @Schema(description = "거래 고유 아이디", example = "1")
    private int transactionId;

    @Schema(description = "유저 고유 아이디", example = "1")
    private int userId;

    @Schema(description = "주식 고유 아이디", example = "1")
    private int stockId;

    @Schema(description = "거래 유형", example = "매수BUY | 매도SELL")
    private String transactionType;

    @Schema(description = "거래 수량")
    private int quantity;

    @Schema(description = "거래 당시 주가")
    private double priceAtTransaction;

    @Schema(description = "거래 시각")
    private LocalDateTime transactionDate;
}
