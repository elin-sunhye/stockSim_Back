package com.elin.stocksim_back.controller;

import com.elin.stocksim_back.dto.request.ReqCommonListDto;
import com.elin.stocksim_back.dto.request.stock.ReqBuyStock;
import com.elin.stocksim_back.security.principal.PrincipalUser;
import com.elin.stocksim_back.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Tag(name = "stock", description = "금융위원회_주식시세정보 API")
@RestController
@RequestMapping("/api/stock")
public class StockController {

//    private final String BASE_URL;
//    private final String SERVICE_KEY;

    @Autowired
    private StockService stockService;

//    public StockController(
//            @Value(value = "${open-api.stock-back-url}") String backUrl,
//            @Value(value = "${open-api.stock-secret-key}") String secretKey
//    ) {
//        this.BASE_URL = backUrl;
//        this.SERVICE_KEY = "?serviceKey=" + secretKey;
//    }

    @Operation(summary = "주식 시세 조회", description = "KRX에 상장된 주식의 시세 정보를 제공")
    @GetMapping("/getStockPriceInfo")
    public ResponseEntity<?> getStockList(@ModelAttribute ReqCommonListDto dto) throws NotFoundException {

//        final String PAGE_NO = "&pageNo=" + dto.getPage();
//        final String NUM_OF_ROWS = "&numOfRows=" + dto.getLimitCount();
//        final String ITEMS_NM = "&itmsNm=" + dto.getSearchText();
//
//        String makeUrl = BASE_URL +
//                "/getStockPriceInfo" +
//                SERVICE_KEY +
//                PAGE_NO +
//                NUM_OF_ROWS +
//                ITEMS_NM + "&resultType=json";

//        System.out.println(makeUrl);

//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
//        ResponseEntity<String> resultMap = restTemplate.exchange(makeUrl, HttpMethod.GET, entity, String.class);

        // 🔍 실제 Content-Type 확인
//        System.out.println("응답 Content-Type: " + resultMap.getHeaders().getContentType());
//        System.out.println("응답 바디: " + resultMap.getBody());


//        System.out.println(resultMap);

//        return ResponseEntity.ok().body(resultMap.getBody());

        return ResponseEntity.ok().body(stockService.getStockList());
    }

    @Operation(summary = "주식 매수", description = "주식 사기")
    @PostMapping("/buyStock")
    public ResponseEntity<String> buyStock(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody ReqBuyStock req
    ) {
        if (stockService.buyPortfolio(principalUser.getUser().getUserId(), req.getStockId(), req.getQuantity())) {
            return ResponseEntity.ok().body("매수 성공");
        } else {
            return ResponseEntity.badRequest().body("매수 실패");
        }

    }
}
