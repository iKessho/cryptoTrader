package com.example.cryptoTrader.controller;

import com.example.cryptoTrader.repository.WalletRepository;
import com.example.cryptoTrader.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok().body(walletService.getWalletsByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<?> getAllWallets() {
        return ResponseEntity.ok().body(walletRepository.findAll());
    }
}
