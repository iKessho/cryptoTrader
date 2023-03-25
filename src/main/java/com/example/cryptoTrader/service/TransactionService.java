package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getAllTransactions(Long userId);

    public List<Transaction> getTransactionsByUserId(Long userId);

    public Transaction trade (Long userId, Transaction transaction);
}
