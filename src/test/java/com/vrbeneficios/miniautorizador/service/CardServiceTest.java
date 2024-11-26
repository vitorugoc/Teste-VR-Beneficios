package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import com.vrbeneficios.miniautorizador.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCard_shouldCreateCardSuccessfully() {
        CardDTO cardDTO = new CardDTO("6549873025634501", "1234");
        when(cardRepository.findById(cardDTO.getCardNumber())).thenReturn(Optional.empty());

        Card createdCard = cardService.createCard(cardDTO);

        assertEquals(cardDTO.getCardNumber(), createdCard.getCardNumber());
        assertEquals(cardDTO.getPassword(), createdCard.getPassword());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowExceptionWhenCardAlreadyExists() {
        CardDTO cardDTO = new CardDTO("6549873025634501", "1234");
        when(cardRepository.findById(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(new Card(cardDTO.getCardNumber(), cardDTO.getPassword())));

        assertThrows(CardAlreadyExistsException.class, () -> cardService.createCard(cardDTO));
    }
}
