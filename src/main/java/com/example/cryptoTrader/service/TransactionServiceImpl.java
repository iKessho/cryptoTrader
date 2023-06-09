package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Price;
import com.example.cryptoTrader.domain.Transaction;
import com.example.cryptoTrader.domain.User;
import com.example.cryptoTrader.domain.Wallet;
import com.example.cryptoTrader.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletService walletService;

    @Autowired
    PriceService priceService;

    @Autowired
    UserService userService;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }


    // In the case of the BTCUSDT trading pair,
    // the bid price would represent the highest price
    // a buyer is willing to pay for bitcoin (BTC) in USDT,
    // while the ask price would represent the lowest price
    // a seller is willing to accept for BTC in USDT.
    // So, if a user wants to buy BTC with USDT,
    // they would typically look at the ask price,
    // and if they want to sell BTC for USDT,
    // they would look at the bid price.
    @Override
    public Transaction trade(Long userId, Transaction transaction) {
        String tradeType = transaction.getType();
        if ("BUY".equalsIgnoreCase(transaction.getType())){
            List<Wallet> userWallet = walletService.getWalletsByUserId(userId);
            Wallet usdtWallet = userWallet.stream()
                    .filter(wallet -> wallet.getCurrency()
                            .equalsIgnoreCase("USDT"))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough balance"));
            String pair = transaction.getCurrency().concat("USDT");
            Price currentPrice = priceService.getPairPrice(pair);
            BigDecimal askPrice = currentPrice.getAskPrice();
            BigDecimal requiredAmt = askPrice.multiply(transaction.getAmount());
            if (usdtWallet.getBalance().compareTo(requiredAmt) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough balance");
            }
            transaction.setPrice(askPrice);
            transaction.setDate(LocalDateTime.now());
            User user = new User();
            user.setId(userId);
            transaction.setUser(user);
            Transaction response = transactionRepository.save(transaction);
            usdtWallet.setBalance(usdtWallet.getBalance().subtract(requiredAmt));
            Wallet targetWallet = walletService.getWalletByUserIdAndCurrency(userId, transaction.getCurrency());
            targetWallet.setBalance(targetWallet.getBalance().add(transaction.getAmount()));
            walletService.updateWallet(userId, usdtWallet);
            walletService.updateWallet(userId, targetWallet);

            return response;
        }

        if ("SELL".equalsIgnoreCase(transaction.getType())){
            List<Wallet> userWallet = walletService.getWalletsByUserId(userId);
            Wallet targetWallet = userWallet.stream()
                    .filter(wallet -> wallet.getCurrency()
                            .equalsIgnoreCase(transaction.getCurrency()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough balance"));
            if (targetWallet.getBalance().compareTo(transaction.getAmount()) < 0 ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough balance");
            }
            String pair = transaction.getCurrency().concat("USDT");
            Price currentPrice = priceService.getPairPrice(pair);
            transaction.setPrice(currentPrice.getBidPrice());
            transaction.setDate(LocalDateTime.now());
            User user = new User();
            user.setId(userId);
            transaction.setUser(user);
            Transaction response = transactionRepository.save(transaction);
            targetWallet.setBalance(targetWallet.getBalance().subtract(transaction.getAmount()));
            Wallet usdtWallet = walletService.getWalletByUserIdAndCurrency(userId, "USDT");
            usdtWallet.setBalance(usdtWallet.getBalance().add(transaction.getAmount().multiply(transaction.getPrice())));
            walletService.updateWallet(userId, targetWallet);
            walletService.updateWallet(userId, usdtWallet);

            return response;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognized Type");
    }
}
