package com.elin.stocksim_back.controller;

import com.elin.stocksim_back.dto.request.stock.ReqBuyStock;
import com.elin.stocksim_back.dto.request.stock.ReqSellStock;
import com.elin.stocksim_back.security.principal.PrincipalUser;
import com.elin.stocksim_back.service.PortfolioService;
import com.elin.stocksim_back.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "portfolio", description = "사용자 주식 매도 매수 API")
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Operation(summary = "주식 매수", description = "주식 사기")
    @PostMapping("/buyStock")
    public ResponseEntity<String> buyStock(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody ReqBuyStock req
    ) throws NotFoundException {

        if (portfolioService.addPortfolio(principalUser.getUser().getUserId(), req.getStockId(), req.getQuantity())) {
            return ResponseEntity.ok().body("매수 성공");
        } else {
            return ResponseEntity.badRequest().body("매수 실패");
        }
    }

    @Operation(summary = "주식 매도", description = "주식 팔기")
    @PutMapping("/sellStock")
    public ResponseEntity<String> sellStock(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody ReqSellStock req
    ) {
        if (portfolioService.updatePortfolio(principalUser.getUser().getUserId(), req.getStockId(), req.getQuantity())) {
            return ResponseEntity.ok().body("매도 성공");
        } else {
            return ResponseEntity.ok().body("매도 실패");
        }
    }
}
