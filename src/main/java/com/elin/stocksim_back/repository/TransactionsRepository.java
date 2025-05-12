package com.elin.stocksim_back.repository;

import com.elin.stocksim_back.mappers.TransactionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionsRepository {

    @Autowired
    private TransactionsMapper transactionsMapper;

//    사용자 주식 거래 내역 전체 조회


    //    사용자 주식 거래 내역 저장
    public int addTransactions(int userId, int stockId, String transactionType, int quantity, double priceAtTransaction) {
        return transactionsMapper.addTransactions(userId, stockId, transactionType, quantity, priceAtTransaction);
    }
}
