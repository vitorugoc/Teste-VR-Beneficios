package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.exception.CardNotFoundException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card createCard(CardDTO cardDTO) {
        cardRepository.findById(cardDTO.getCardNumber())
                .ifPresent(card -> {
                    throw new CardAlreadyExistsException("Card already exists");
                });

        Card newCard = new Card(cardDTO.getCardNumber(), cardDTO.getPassword());
        return cardRepository.save(newCard);
    }

    public BigDecimal getBalance(String cardNumber) {
        return cardRepository.findById(cardNumber)
                .map(Card::getBalance)
                .orElseThrow(() -> new CardNotFoundException("Card not found: " + cardNumber));
    }
}
