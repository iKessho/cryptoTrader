package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Price;
import com.example.cryptoTrader.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class Poller {

    @Autowired
    PriceRepository priceRepository;

    @Scheduled (fixedRate = 10000)
    public void fetchPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
        String huobiUrl = "https://api.huobi.pro/market/tickers";

        try {
            ResponseEntity<Object[]> binanceResponse = restTemplate.getForEntity(binanceUrl, Object[].class);
            for (Object obj : binanceResponse.getBody()) {
                Map<String, Object> map = (Map<String, Object>) obj;
                String symbol = (String) map.get("symbol");
                if (symbol.equals("ETHUSDT") || symbol.equals("BTCUSDT")) {
                    BigDecimal bidPrice = new BigDecimal((String) map.get("bidPrice"));
                    BigDecimal askPrice = new BigDecimal((String) map.get("askPrice"));
                    Price price = new Price();
                    price.setPair(symbol);
                    price.setBidPrice(bidPrice);
                    price.setAskPrice(askPrice);
                    price.setDate(LocalDateTime.now());
                    priceRepository.save(price);
                }
            }

        } catch (Exception e) {
            // Do nth
        }
    }
}
