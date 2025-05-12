package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMapper {
    //    주식 전체 조회
    List<Stock> getStockList();

    //    주식 단건 조회
    Stock getStockByStockId(@Param("stockId") int stockId);

    //    스케줄 주식 업데이트 저장
    int upsertStock(
            @Param("basDt") String basDt,
            @Param("srtnCd") String srtnCd,
            @Param("isinCd") String isinCd,
            @Param("itmsNm") String itmsNm,
            @Param("mrktCtg") String mrktCtg,
            @Param("clpr") int clpr,
            @Param("vs") int vs,
            @Param("fltRt") int fltRt,
            @Param("mkp") int mkp,
            @Param("hipr") int hipr,
            @Param("lopr") int lopr,
            @Param("trqu") int trqu,
            @Param("trPrc") int trPrc,
            @Param("lstgStCnt") int lstgStCnt,
            @Param("mrktTotAmt") int mrktTotAmt
    );
}
