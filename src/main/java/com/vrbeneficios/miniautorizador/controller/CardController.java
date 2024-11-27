package com.vrbeneficios.miniautorizador.controller;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        try {
            Card newCard = cardService.createCard(cardDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CardDTO(newCard.getCardNumber(), newCard.getPassword()));
        } catch (CardAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cardDTO);
        }
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<?> getBalance(@PathVariable String cardNumber) {
        try {
            BigDecimal balance = cardService.getBalance(cardNumber);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
