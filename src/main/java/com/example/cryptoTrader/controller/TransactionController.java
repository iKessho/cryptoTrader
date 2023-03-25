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
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok().body(transactionRepository.findById(userId).orElse(null));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> trade (@PathVariable Long userId, @RequestBody Transaction transaction) {
        transactionService.trade(userId, transaction);
        return null;
    }
}
