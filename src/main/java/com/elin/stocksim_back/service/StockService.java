package com.elin.stocksim_back.service;

import com.elin.stocksim_back.entity.Portfolio;
import com.elin.stocksim_back.entity.Stock;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.NotFoundValueException;
import com.elin.stocksim_back.repository.PortfolioRepository;
import com.elin.stocksim_back.repository.StockRepository;
import com.elin.stocksim_back.repository.TransactionsRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    //    주식 전체 조회
    public List<Stock> getStockList() throws NotFoundException {
        return stockRepository.getStockList().orElseThrow(() -> new NotFoundException("데이터가 없습니다."));
    }

    //    사용자 현재 보유 주식 매수
    public boolean buyPortfolio(int userId, int stockId, int quantity) {

        Stock getStockInfo = stockRepository.getStockByStockId(stockId);

        int saveTransaction = transactionsRepository.addTransactions(userId, stockId, "BUY", quantity, getStockInfo.getClpr());

        if (saveTransaction > 0) {
            Optional<Portfolio> portfolioOpt = portfolioRepository.getPortfolioByUserIdAndStockId(userId, stockId);

            if (portfolioOpt.isEmpty()) {
//            최초 매수하는 경우 저장
                if (portfolioRepository.buyPortfolio(userId, stockId, quantity) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
//            이미 매수한 이력이 있는 경우 업데이트
                int pastQuantity = portfolioOpt.get().getQuantity();
                int updateQuantity = pastQuantity + quantity;

                if (updateQuantity > 0) {
                    if (portfolioRepository.updatePortfolioByUserIdAndStockId(userId, stockId, updateQuantity) > 0) {
                        return true;
                    } else if (updateQuantity == 0) {
                        if (portfolioRepository.deletePortfolioByUserIdAndStockId(userId, stockId) > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
