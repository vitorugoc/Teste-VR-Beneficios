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

    public void processTransaction(TransactionDTO transactionDTO) {
        Card card = cardRepository.findByCardNumber(transactionDTO.getCardNumber())
                .orElseThrow(() -> new TransactionException("CARTAO_INEXISTENTE"));

        if (!card.getPassword().equals(transactionDTO.getCardPassword())) {
            throw new TransactionException("SENHA_INVALIDA");
        }

        if (card.getBalance().compareTo(transactionDTO.getValue()) < 0) {
            throw new TransactionException("SALDO_INSUFICIENTE");
        }

        card.setBalance(card.getBalance().subtract(transactionDTO.getValue()));
        cardRepository.save(card);
    }
}
