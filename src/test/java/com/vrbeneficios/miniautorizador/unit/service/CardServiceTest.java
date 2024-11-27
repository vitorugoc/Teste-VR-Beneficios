package com.vrbeneficios.miniautorizador.unit.service;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.exception.CardAlreadyExistsException;
import com.vrbeneficios.miniautorizador.exception.CardNotFoundException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import com.vrbeneficios.miniautorizador.service.CardService;
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

    private final String cardNumber = "6549873025634501";
    private final String cardPassword = "1234";
    private final BigDecimal initialBalance = BigDecimal.valueOf(500.00);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CardDTO createCardDTO() {
        return new CardDTO("6549873025634501", "1234");
    }

    private Card createCard(String cardNumber, String password, BigDecimal balance) {
        return new Card(cardNumber, password, balance);
    }

    @Test
    void createCard_shouldCreateCardSuccessfully() {
        CardDTO cardDTO = createCardDTO();
        Card mockCard = createCard(cardNumber, cardPassword, initialBalance);

        when(cardRepository.findById(cardDTO.getCardNumber())).thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class))).thenReturn(mockCard);

        Card createdCard = cardService.createCard(cardDTO);

        assertEquals(cardDTO.getCardNumber(), createdCard.getCardNumber());
        assertEquals(cardDTO.getPassword(), createdCard.getPassword());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowExceptionWhenCardAlreadyExists() {
        CardDTO cardDTO = createCardDTO();
        when(cardRepository.findById(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(createCard(cardDTO.getCardNumber(), cardDTO.getPassword(), initialBalance)));

        assertThrows(CardAlreadyExistsException.class, () -> cardService.createCard(cardDTO));
    }

    @Test
    void getBalance_shouldReturnBalanceWhenCardExists() {
        Card card = createCard(cardNumber, cardPassword, BigDecimal.valueOf(495.15));
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));

        BigDecimal actualBalance = cardService.getBalance(cardNumber);

        assertEquals(card.getBalance(), actualBalance);
        verify(cardRepository, times(1)).findById(cardNumber);
    }

    @Test
    void getBalance_shouldThrowExceptionWhenCardDoesNotExist() {
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.getBalance(cardNumber));
        verify(cardRepository, times(1)).findById(cardNumber);
    }
}
