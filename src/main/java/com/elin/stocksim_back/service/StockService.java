package com.elin.stocksim_back.service;

import com.elin.stocksim_back.entity.Stock;
import com.elin.stocksim_back.repository.StockRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    //    주식 전체 조회
    public List<Stock> getStockList() throws NotFoundException {
        return stockRepository.getStockList().orElseThrow(() -> new NotFoundException("데이터가 없습니다."));
    }
}
