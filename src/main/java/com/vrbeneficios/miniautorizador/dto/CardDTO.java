package com.vrbeneficios.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    @JsonProperty("numeroCartao")
    @NotBlank(message = "O número do cartão é obrigatório.")
    private String cardNumber;

    @JsonProperty("senha")
    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}
