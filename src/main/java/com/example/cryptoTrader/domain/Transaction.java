package com.example.cryptoTrader.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    private String type;

    private String currency;

    private BigDecimal amount;

    private BigDecimal price;

    private LocalDateTime date;
}
