package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.Price;
import com.example.cryptoTrader.repository.PriceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Scheduled(fixedRate = 10000)
    public void fetchPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
        String huobiUrl = "https://api.huobi.pro/market/tickers";

        try {
            Price ethPrice = new Price();
            ethPrice.setDate(LocalDateTime.now());
            ethPrice.setPair("ETHUSDT");
            Price btcPrice = new Price();
            btcPrice.setDate(LocalDateTime.now());
            btcPrice.setPair("BTCUSDT");

            ResponseEntity<Object[]> binanceResponse = restTemplate.getForEntity(binanceUrl, Object[].class);
            for (Object obj : binanceResponse.getBody()) {
                Map<String, Object> map = (Map<String, Object>) obj;
                String symbol = (String) map.get("symbol");
                if ("ETHUSDT".equalsIgnoreCase(symbol) || "BTCUSDT".equalsIgnoreCase(symbol)) {
                    BigDecimal bidPrice = new BigDecimal((String) map.get("bidPrice"));
                    BigDecimal askPrice = new BigDecimal((String) map.get("askPrice"));

                    if ("ETHUSDT".equalsIgnoreCase(symbol)) {
                        ethPrice.setBidPrice(bidPrice);
                        ethPrice.setAskPrice(askPrice);
                    } else {
                        btcPrice.setBidPrice(bidPrice);
                        btcPrice.setAskPrice(askPrice);
                    }

                }
            }

            ResponseEntity<String> response = restTemplate.getForEntity(huobiUrl, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            for (JsonNode ticker : root.get("data")) {
                String symbol = ticker.get("symbol").asText();
                if ("ETHUSDT".equalsIgnoreCase(symbol) || "BTCUSDT".equalsIgnoreCase(symbol)) {
                    BigDecimal bidPrice = ticker.get("bid").decimalValue();
                    BigDecimal askPrice = ticker.get("ask").decimalValue();

                    if ("ETHUSDT".equalsIgnoreCase(symbol)) {
                        if (ethPrice.getBidPrice().compareTo(bidPrice) < 0) {
                            ethPrice.setBidPrice(bidPrice);
                        }
                        if (ethPrice.getAskPrice().compareTo(askPrice) > 0) {
                            ethPrice.setAskPrice(askPrice);
                        }
                    } else {
                        if (btcPrice.getBidPrice().compareTo(bidPrice) < 0) {
                            btcPrice.setBidPrice(bidPrice);
                        }
                        if (btcPrice.getAskPrice().compareTo(askPrice) > 0) {
                            btcPrice.setAskPrice(askPrice);
                        }
                    }

                }
            }

            priceRepository.save(ethPrice);
            priceRepository.save(btcPrice);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
