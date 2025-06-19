package com.elin.stocksim_back.service;

import com.elin.stocksim_back.entity.Portfolio;
import com.elin.stocksim_back.entity.Stock;
import com.elin.stocksim_back.repository.PortfolioRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    //    사용자 해당 주식 조회
    public Portfolio getPortfolioByUserIdAndStockId(int userId, int stockId) throws NotFoundException {
        return portfolioRepository.getPortfolioByUserIdAndStockId(userId, stockId).orElseThrow(() -> new NotFoundException("데이터가 없습니다."));
    }


    //    주식 매수
    public boolean addPortfolio(int userId, int stockId, int quantity) {

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

            if (quantity <= 0 || pastQuantity >= updateQuantity || updateQuantity <= 0) {
                return false;
            }

            if (portfolioRepository.updatePortfolioQuantityByUserIdAndStockId(userId, stockId, updateQuantity) > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    //    주식 매도
    public boolean updatePortfolio(int userId, int stockId, int quantity) {
        Optional<Portfolio> portfolioOpt = portfolioRepository.getPortfolioByUserIdAndStockId(userId, stockId);

        int pastQuantity = portfolioOpt.get().getQuantity();
        int updateQuantity = pastQuantity - quantity;

        if (quantity <= 0 || pastQuantity <= updateQuantity || updateQuantity < 0) {
            return false;
        } else if (updateQuantity == 0) {
            if (portfolioRepository.deletePortfolioByUserIdAndStockId(userId, stockId) > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (portfolioRepository.updatePortfolioQuantityByUserIdAndStockId(userId, stockId, updateQuantity) > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
