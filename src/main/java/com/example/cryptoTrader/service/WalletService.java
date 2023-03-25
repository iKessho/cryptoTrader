package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Wallet;

import java.util.List;

public interface WalletService {
    public List<Wallet> getWalletsByUserId(Long userId);

    public Wallet getWalletByUserIdAndCurrency(Long userId, String currency);

    public Wallet updateWallet(Long userId, Wallet wallet);
}
