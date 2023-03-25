package com.example.cryptoTrader.repository;

import com.example.cryptoTrader.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
