package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.entity.Portfolio;
import com.elin.stocksim_back.mappers.PortfolioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PortfolioRepository {
    @Autowired
    private PortfolioMapper portfolioMapper;

    //    사용자 현재 보유 주식 전체 조회
    public Optional<List<Portfolio>> getPortfolioByUserId(int userId) {
        return Optional.ofNullable(portfolioMapper.getPortfolioByUserId(userId));
    }

    //    사용자 현재 보유 주식 단건 조회
    public Optional<Portfolio> getPortfolioByUserIdAndStockId(int userId, int stockId) {
        return Optional.ofNullable(portfolioMapper.getPortfolioByUserIdAndStockId(userId, stockId));
    }

    //    사용자 현재 보유 주식 매수
    public int buyPortfolio(int userId, int stockId, int quantity) {
        return portfolioMapper.buyPortfolio(userId, stockId, quantity);
    }

    //    사용자 현재 보유 주식 업데이트 (매도, 매수)
    public int updatePortfolioByUserIdAndStockId(int userId, int stockId, int quantity) {
        return portfolioMapper.updatePortfolioByUserIdAndStockId(userId, stockId, quantity);
    }

    //    사용자 현재 보유 주식 삭제
    public int deletePortfolioByUserIdAndStockId(int userId, int stockId) {
        return portfolioMapper.deletePortfolioByUserIdAndStockId(userId, stockId);
    }
}
