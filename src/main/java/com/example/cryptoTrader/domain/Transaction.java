package com.example.cryptoTrader.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JsonIgnore
    private User user;

    @NotNull
    private String type;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal amount;

    private BigDecimal price;

    private LocalDateTime date;
}
