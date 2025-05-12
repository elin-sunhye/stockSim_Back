package com.elin.stocksim_back.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "portfolio", description = "사용자 주식 매도 매수 API")
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
}
