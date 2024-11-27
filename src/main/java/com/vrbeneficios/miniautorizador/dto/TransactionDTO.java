package com.vrbeneficios.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @JsonProperty("numeroCartao")
    private String cardNumber;
    @JsonProperty("senhaCartao")
    private String cardPassword;
    @JsonProperty("valor")
    private BigDecimal value;
}
