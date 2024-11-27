package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.TransactionDTO;
import com.vrbeneficios.miniautorizador.exception.TransactionException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepository;

    public void processTransaction(TransactionDTO transactionDTO) {
        Card card = cardRepository.findByCardNumber(transactionDTO.getCardNumber())
                .orElseThrow(() -> new TransactionException("CARTAO_INEXISTENTE"));

        validatePassword(card, transactionDTO.getCardPassword());
        validateBalance(card, transactionDTO.getValue());

        card.setBalance(card.getBalance().subtract(transactionDTO.getValue()));
        cardRepository.save(card);
    }

    private void validatePassword(Card card, String password) {
        if (!card.getPassword().equals(password)) {
            throw new TransactionException("SENHA_INVALIDA");
        }
    }

    private void validateBalance(Card card, BigDecimal amount) {
        if (card.getBalance().compareTo(amount) < 0) {
            throw new TransactionException("SALDO_INSUFICIENTE");
        }
    }
}
