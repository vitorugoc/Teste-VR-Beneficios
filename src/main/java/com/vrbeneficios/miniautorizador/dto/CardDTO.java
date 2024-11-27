package com.vrbeneficios.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    @JsonProperty("numeroCartao")
    private String cardNumber;
    @JsonProperty("senha")
    private String password;
}
