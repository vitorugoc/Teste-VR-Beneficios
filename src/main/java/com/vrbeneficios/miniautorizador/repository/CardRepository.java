package com.vrbeneficios.miniautorizador.repository;

import com.vrbeneficios.miniautorizador.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
