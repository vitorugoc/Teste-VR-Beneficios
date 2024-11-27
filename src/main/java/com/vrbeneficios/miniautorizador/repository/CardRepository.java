package com.vrbeneficios.miniautorizador.repository;

import com.vrbeneficios.miniautorizador.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    Optional<Card> findByCardNumber(String cardNumber);
}
