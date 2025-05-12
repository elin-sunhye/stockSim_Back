package com.elin.stocksim_back.mappers;

import com.elin.stocksim_back.entity.Transactions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionsMapper {
    //    사용자 주식 거래 내역 전체 조회
    List<Transactions> getTransactionsByUserId(@Param("userId") int userId);

    //    사용자 주식 거래 내역 저장
    int addTransactions(
            @Param("userId") int userId,
            @Param("stockId") int stockId,
            @Param("transactionType") String transactionType,
            @Param("quantity") int quantity,
            @Param("priceAtTransaction") double priceAtTransaction
    );
}
