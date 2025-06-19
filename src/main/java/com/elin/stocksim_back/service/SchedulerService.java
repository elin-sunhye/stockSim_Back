package com.elin.stocksim_back.service;

import com.elin.stocksim_back.repository.StockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@EnableScheduling
public class SchedulerService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL;
    private final String SERVICE_KEY;

    public SchedulerService(
            @Value(value = "${open-api.stock-back-url}") String backUrl,
            @Value(value = "${open-api.stock-secret-key}") String secretKey
    ) {
        this.BASE_URL = backUrl;
        this.SERVICE_KEY = "?serviceKey=" + secretKey;
    }

    @Scheduled(cron = "${schedule.cron}")
    @Transactional(rollbackFor = Exception.class)
    public void upsertStock() throws JsonProcessingException {
//        매일 밤 12시에 실행해서 realStock entity를 stock emtity로 변경해서 stock_tb에 저장하는 코드 필요

        final String NUM_OF_ROWS = "&numOfRows=300";
        final String PAGE_NO = "&pageNo=1";
        final String RESULT_TYPE = "&resultType=json";
//        final String ITEMS_NM = "&itmsNm=" + dto.getSearchText();

        String makeUrl = BASE_URL +
                "/getStockPriceInfo" +
                SERVICE_KEY +
                NUM_OF_ROWS +
                PAGE_NO +
                RESULT_TYPE;

//        System.out.println(makeUrl);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<String> resultMap = restTemplate.exchange(makeUrl, HttpMethod.GET, entity, String.class);
        String body = resultMap.getBody();

//        조회한 결과 DB에 저장
        JsonNode root = objectMapper.readTree(body);  // 전체 JSON
        JsonNode items = root.path("response").path("body").path("items").path("item");

        for (JsonNode item : items) {
            String basDt = item.get("basDt").asText();
            String srtnCd = item.get("srtnCd").asText();
            String isinCd = item.get("isinCd").asText();
            String itmsNm = item.get("itmsNm").asText();
            String mrktCtg = item.get("mrktCtg").asText();
            int clpr = item.get("clpr").asInt();
            int vs = item.get("vs").asInt();
            int fltRt = item.get("fltRt").asInt();
            int mkp = item.get("mkp").asInt();
            int hipr = item.get("hipr").asInt();
            int lopr = item.get("lopr").asInt();
            int trqu = item.get("trqu").asInt();
            int trPrc = item.get("trPrc").asInt();
            int lstgStCnt = item.get("lstgStCnt").asInt();
            int mrktTotAmt = item.get("mrktTotAmt").asInt();

            // DB에 UPSERT 처리 (on duplicate key update 또는 insert)
            stockRepository.upsertStock(basDt, srtnCd, isinCd, itmsNm, mrktCtg, clpr, vs, fltRt, mkp, hipr, lopr, trqu, trPrc, lstgStCnt, mrktTotAmt);
        }


//        if (!body.isEmpty()) {
//            // Jackson ObjectMapper 객체 생성
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // JSON 데이터를 Product 객체로 변환
//            RealStock realStock = objectMapper.readValue(body, RealStock.class);
//
//            stockRepository.upsertStock(
//                    realStock.getBasDt(),
//                    realStock.getSrtnCd(),
//                    realStock.getIsinCd(),
//                    realStock.getItmsNm(),
//                    realStock.getMrktCtg(),
//                    realStock.getClpr(),
//                    realStock.getVs(),
//                    realStock.getFltRt(),
//                    realStock.getMkp(),
//                    realStock.getHipr(),
//                    realStock.getLopr(),
//                    realStock.getTrqu(),
//                    realStock.getTrPrc(),
//                    realStock.getLstgStCnt(),
//                    realStock.getMrktTotAmt()
//            );
//        }


        // 🔍 실제 Content-Type 확인
//        System.out.println("응답 Content-Type: " + resultMap.getHeaders().getContentType());
//        System.out.println("응답 바디: " + resultMap.getBody());


//        System.out.println(resultMap);

//        return ResponseEntity.ok().body(resultMap.getBody());

    }
}
