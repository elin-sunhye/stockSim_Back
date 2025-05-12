package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.Portfolio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    //    사용자 현재 보유 주식 전체 조회
    List<Portfolio> getPortfolioByUserId(@Param("userId") int userId);

    //    사용자 현재 보유 주식 단건 조회
    Portfolio getPortfolioByUserIdAndStockId(@Param("userId") int userId, @Param("stockId") int stockId);

    //    사용자 현재 보유 주식 매수
    int buyPortfolio(
            @Param("userId") int userId,
            @Param("stockId") int stockId,
            @Param("quantity") int quantity
    );

    //    사용자 현재 보유 주식 업데이트 (매도, 매수)
    int updatePortfolioByUserIdAndStockId(
            @Param("userId") int userId,
            @Param("stockId") int stockId,
            @Param("quantity") int quantity
    );

    //    사용자 현재 보유 주식 삭제
    int deletePortfolioByUserIdAndStockId(
            @Param("userId") int userId,
            @Param("stockId") int stockId
    );
}
