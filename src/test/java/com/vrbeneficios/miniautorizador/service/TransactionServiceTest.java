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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionService transactionService;

    private final String cardNumber = "6549873025634501";
    private final String password = "1234";
    private final BigDecimal initialBalance = BigDecimal.valueOf(500.00);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TransactionDTO createTransactionDTO(String cardNumber, String password, BigDecimal amount) {
        return new TransactionDTO(cardNumber, password, amount);
    }

    private Card createCard(BigDecimal balance) {
        return new Card("6549873025634501", "1234", balance);
    }

    @Test
    void shouldAuthorizeTransactionWhenBalanceIsSufficientAndPasswordIsCorrect() {
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        Card card = createCard(initialBalance);
        TransactionDTO transaction = createTransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        transactionService.processTransaction(transaction);

        card.setBalance(card.getBalance().subtract(transactionAmount));
        verify(cardRepository, times(1)).save(card);
        verify(cardRepository, times(1)).findByCardNumber(cardNumber);
    }

    @Test
    void shouldThrowExceptionWhenCardDoesNotExist() {
        String cardNumber = "0000000000000000";
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        TransactionDTO transaction = createTransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.empty());

        assertThrows(TransactionException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        String wrongPassword = "wrong";
        BigDecimal transactionAmount = BigDecimal.valueOf(10.00);
        Card card = createCard(initialBalance);
        TransactionDTO transaction = createTransactionDTO(cardNumber, wrongPassword, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        assertThrows(TransactionException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        BigDecimal transactionAmount = BigDecimal.valueOf(600.00);
        Card card = createCard(initialBalance);
        TransactionDTO transaction = createTransactionDTO(cardNumber, password, transactionAmount);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        assertThrows(TransactionException.class, () -> transactionService.processTransaction(transaction));
    }
}
