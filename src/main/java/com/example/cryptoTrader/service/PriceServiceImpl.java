package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Price;
import com.example.cryptoTrader.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;
    @Override
    public Price getPairPrice(String pair) {
        return priceRepository.findByPairIgnoreCase(pair).orElse(null);
    }

    @Override
    public List<Price> getAllPairsPrice() {
        return priceRepository.findAll();
    }
}
