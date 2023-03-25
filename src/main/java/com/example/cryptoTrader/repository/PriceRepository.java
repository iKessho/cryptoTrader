package com.example.cryptoTrader.repository;

import com.example.cryptoTrader.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
}
