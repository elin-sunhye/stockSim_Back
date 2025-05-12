package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.entity.Stock;
import com.elin.stocksim_back.mappers.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StockRepository {
    @Autowired
    private StockMapper stockMapper;

    //    주식 전체 조회
    public Optional<List<Stock>> getStockList() {
        return Optional.ofNullable(stockMapper.getStockList());
    }

    //    주식 단건 조회
    public Stock getStockByStockId(int stockId) {
        return stockMapper.getStockByStockId(stockId);
    }

    //    주식 upsert
    public boolean upsertStock(
            String basDt,
            String srtnCd,
            String isinCd,
            String itmsNm,
            String mrktCtg,
            int clpr,
            int vs,
            int fltRt,
            int mkp,
            int hipr,
            int lopr,
            int trqu,
            int trPrc,
            int lstgStCnt,
            int mrktTotAmt
    ) {
        int upsert = stockMapper.upsertStock(basDt, srtnCd, isinCd, itmsNm, mrktCtg, clpr, vs, fltRt, mkp, hipr, lopr, trqu, trPrc, lstgStCnt, mrktTotAmt);
        if (upsert > 0) {
            return true;
        } else {
            return false;
        }
    }
}
