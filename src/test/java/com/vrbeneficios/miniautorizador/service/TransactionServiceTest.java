package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.TransactionDTO;
import com.vrbeneficios.miniautorizador.exception.TransactionException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAuthorizeTransactionWhenBalanceIsSufficientAndPasswordIsCorrect() {
        String cardNumber = "6549873025634501";
        String password = "1234";
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        Card card = new Card(cardNumber, password, BigDecimal.valueOf(500.00));
        TransactionDTO transaction = new TransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        transactionService.processTransaction(transaction);

        verify(cardRepository, times(1)).save(card);
        verify(cardRepository, times(1)).findByCardNumber(cardNumber);
    }

    @Test
    void shouldThrowExceptionWhenCardDoesNotExist() {
        String cardNumber = "0000000000000000";
        String password = "1234";
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        TransactionDTO transaction = new TransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.empty());

        assertThrows(TransactionException.class, () ->
                transactionService.processTransaction(transaction)
        );
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        String cardNumber = "6549873025634501";
        String password = "wrong";
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        Card card = new Card(cardNumber, "1234", BigDecimal.valueOf(500.00));
        TransactionDTO transaction = new TransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        assertThrows(TransactionException.class, () ->
                transactionService.processTransaction(transaction)
        );
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        String cardNumber = "6549873025634501";
        String password = "1234";
        BigDecimal transactionAmount = BigDecimal.valueOf(600.00);
        Card card = new Card(cardNumber, password, BigDecimal.valueOf(500.00));
        TransactionDTO transaction = new TransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        assertThrows(TransactionException.class, () ->
                transactionService.processTransaction(transaction)
        );
    }
}
