package com.example.cryptoTrader.repository;

import com.example.cryptoTrader.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
    Optional<Price> findByPairIgnoreCase(String pair);
}
