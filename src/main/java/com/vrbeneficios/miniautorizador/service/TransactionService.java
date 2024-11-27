package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.TransactionDTO;
import com.vrbeneficios.miniautorizador.exception.TransactionException;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepository;

    public void processTransaction(TransactionDTO transaction) {
        Card card = cardRepository.findByCardNumber(transaction.getCardNumber())
                .orElseThrow(() -> new TransactionException("CARTAO_INEXISTENTE"));

        if (!card.getPassword().equals(transaction.getCardPassword())) {
            throw new TransactionException("SENHA_INVALIDA");
        }

        if (card.getBalance().compareTo(transaction.getValue()) < 0) {
            throw new TransactionException("SALDO_INSUFICIENTE");
        }

        card.setBalance(card.getBalance().subtract(transaction.getValue()));
        cardRepository.save(card);
    }
}
