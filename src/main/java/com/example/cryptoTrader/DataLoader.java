package com.example.cryptoTrader;

import com.example.cryptoTrader.domain.User;
import com.example.cryptoTrader.domain.Wallet;
import com.example.cryptoTrader.repository.UserRepository;
import com.example.cryptoTrader.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("test user");
        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setCurrency("USDT");
        wallet.setBalance(BigDecimal.valueOf(50000));
        wallet.setUser(user);
        walletRepository.save(wallet);
    }
}
