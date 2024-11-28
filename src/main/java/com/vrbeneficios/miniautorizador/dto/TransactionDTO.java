package com.vrbeneficios.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @JsonProperty("numeroCartao")
    @NotBlank(message = "O número do cartão é obrigatório.")
    private String cardNumber;

    @JsonProperty("senhaCartao")
    @NotBlank(message = "A senha é obrigatória.")
    private String cardPassword;

    @JsonProperty("valor")
    @NotNull(message = "O valor da transação é obrigatório.")
    private BigDecimal value;
}
