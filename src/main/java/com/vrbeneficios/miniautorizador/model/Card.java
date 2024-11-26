package com.vrbeneficios.miniautorizador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    private String cardNumber;
    private String password;
    private BigDecimal balance = BigDecimal.valueOf(500.00);

    public Card(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }
}
