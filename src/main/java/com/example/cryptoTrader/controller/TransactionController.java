package com.example.cryptoTrader.controller;

import com.example.cryptoTrader.domain.Transaction;
import com.example.cryptoTrader.repository.TransactionRepository;
import com.example.cryptoTrader.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * Get all transactions from everyone. TODO: restrict to admins only
     * @return List of transactions
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok().body(transactionService.getAllTransactions());
    }

    /**
     * Get all transactions from specified userId.
     * TODO: allow the userId to be retrieved via SecurityContext.
     * @param userId
     * @return List of transactions
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok().body(transactionService.getTransactionsByUserId(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> trade (@PathVariable Long userId, @RequestBody Transaction transaction) {
        return ResponseEntity.ok().body(transactionService.trade(userId, transaction));
    }
}
