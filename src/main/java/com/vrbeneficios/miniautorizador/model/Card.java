package com.vrbeneficios.miniautorizador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Card {
    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo = BigDecimal.valueOf(500.00);
}
