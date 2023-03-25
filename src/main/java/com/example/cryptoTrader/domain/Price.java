package com.example.cryptoTrader.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Price {

    @Id
    private String pair;

    private BigDecimal bidPrice;

    private BigDecimal askPrice;

    private LocalDateTime date;
}
