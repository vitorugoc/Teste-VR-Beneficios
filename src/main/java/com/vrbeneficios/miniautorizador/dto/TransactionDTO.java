package com.vrbeneficios.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String cardNumber;
    private String cardPassword;
    private BigDecimal value;
}
