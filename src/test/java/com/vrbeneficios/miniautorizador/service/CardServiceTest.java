package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.exception.CardNotFoundException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
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
        Card mockCard = new Card(cardDTO.getCardNumber(), cardDTO.getPassword(), BigDecimal.valueOf(500.00));

        when(cardRepository.findById(cardDTO.getCardNumber())).thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class))).thenReturn(mockCard);

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

    @Test
    void getBalance_shouldReturnBalanceWhenCardExists() {
        String cardNumber = "6549873025634501";
        BigDecimal expectedBalance = BigDecimal.valueOf(495.15);
        Card card = new Card(cardNumber, "1234", expectedBalance);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));

        BigDecimal actualBalance = cardService.getBalance(cardNumber);

        assertEquals(expectedBalance, actualBalance);
        verify(cardRepository, times(1)).findById(cardNumber);
    }

    @Test
    void getBalance_shouldThrowExceptionWhenCardDoesNotExist() {
        String cardNumber = "6549873025634501";
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.getBalance(cardNumber));
        verify(cardRepository, times(1)).findById(cardNumber);
    }
}
