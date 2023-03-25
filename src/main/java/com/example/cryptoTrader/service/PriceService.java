package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Price;

import java.util.List;

public interface PriceService {
    public Price getPairPrice(String pair);

    public List<Price> getAllPairsPrice();
}
