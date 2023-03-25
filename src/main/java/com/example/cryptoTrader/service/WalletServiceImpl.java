package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Wallet;
import com.example.cryptoTrader.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public Wallet getWalletByUserIdAndCurrency(Long userId, String currency) {
        Wallet wallet = walletRepository.findByUserIdAndCurrencyIgnoreCase(userId, currency).orElse(null);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setCurrency(currency.toUpperCase());
            wallet.setBalance(new BigDecimal(0));
            wallet.setUser(userService.getUserById(userId));
            wallet = walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet updateWallet(Long userId, Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
