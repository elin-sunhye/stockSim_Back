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
@Schema(description = "주식 시세 정보")
public class Stock {
    @Schema(description = "기준일자")
    private String basDt;

    @Schema(description = "종목 코드보다 짧으면서 유일성이 보장되는 코드(6자리)")
    private String srtnCd;

    @Schema(description = "국제 채권 식별 번호. 유가증권(채권)의 국제인증 고유번호")
    private String isinCd;

    @Schema(description = "종목의 명칭")
    private String itmsNm;

    @Schema(description = "주식의 시장 구분 (KOSPI/KOSDAQ/KONEX 중 1)")
    private String mrktCtg;

    @Schema(description = "정규시장의 매매시간 종료 시까지 형성되는 최종가격")
    private int clpr;

    @Schema(description = "전일 대비 등락")
    private int vs;

    @Schema(description = "전일 대비 등락에 따른 비율")
    private int fltRt;

    @Schema(description = "정규시장의 매매시간 개시 후 형성되는 최초가격")
    private int mkp;

    @Schema(description = "하루 중 가격의 최고치")
    private int hipr;

    @Schema(description = "하루 중 가격의 최저치")
    private int lopr;

    @Schema(description = "체결수량의 누적 합계")
    private int trqu;

    @Schema(description = "거래건 별 체결가격 * 체결수량의 누적 합계")
    private int trPrc;

    @Schema(description = "종목의 상장주식수")
    private int lstgStCnt;

    @Schema(description = "종가 * 상장주식수")
    private int mrktTotAmt;
}
