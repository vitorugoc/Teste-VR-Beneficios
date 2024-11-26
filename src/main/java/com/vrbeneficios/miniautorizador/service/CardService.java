package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card createCard(CardDTO cardDTO) {
        cardRepository.findById(cardDTO.getCardNumber())
                .ifPresent(card -> { throw new CardAlreadyExistsException("Card already exists"); });

        Card newCard = new Card(cardDTO.getCardNumber(), cardDTO.getPassword());
        return cardRepository.save(newCard);
    }
}
