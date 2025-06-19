package com.elin.stocksim_back.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "주식 매도 DTO")
public class ReqSellStock {
    @Schema(description = "주식 고유 아이디", example = "1")
    private int stockId;

    @Schema(description = "주식 수량", example = "1")
    private int quantity;
}
