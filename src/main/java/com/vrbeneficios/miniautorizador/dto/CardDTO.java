package com.vrbeneficios.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private String cardNumber;
    private String password;
}
