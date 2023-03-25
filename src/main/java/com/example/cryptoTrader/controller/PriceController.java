package com.example.cryptoTrader.controller;

import com.example.cryptoTrader.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {
    @Autowired
    PriceRepository priceRepository;

    @GetMapping
    public ResponseEntity<?> getPrice() {
        return ResponseEntity.ok().body(priceRepository.findAll());
    }
}
