package com.vrbeneficios.miniautorizador.integration.controller;

import com.vrbeneficios.miniautorizador.dto.CardDTO;
import com.vrbeneficios.miniautorizador.model.Card;
import com.vrbeneficios.miniautorizador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CardControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setupDatabase() {
        cardRepository.deleteAll();
        cardRepository.save(new Card("6549873025634501", "1234", BigDecimal.valueOf(500.00)));
    }

    @Test
    void shouldCreateCardSuccessfully() {
        CardDTO cardDTO = new CardDTO("1111222233334444", "5678");

        ResponseEntity<CardDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/cartoes",
                cardDTO,
                CardDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cardDTO.getCardNumber(), Objects.requireNonNull(response.getBody()).getCardNumber());
        assertEquals(cardDTO.getPassword(), response.getBody().getPassword());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenCardAlreadyExists() {
        CardDTO cardDTO = new CardDTO("6549873025634501", "1234");

        ResponseEntity<CardDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/cartoes",
                cardDTO,
                CardDTO.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(cardDTO.getCardNumber(), Objects.requireNonNull(response.getBody()).getCardNumber());
    }

    @Test
    void shouldReturnBalanceForExistingCard() {
        String cardNumber = "6549873025634501";

        ResponseEntity<BigDecimal> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/cartoes/" + cardNumber,
                BigDecimal.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, BigDecimal.valueOf(500.00).compareTo(response.getBody()), "The balances are not equal");
    }

    @Test
    void shouldReturnNotFoundForNonExistentCard() {
        String cardNumber = "0000111122223333";

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/cartoes/" + cardNumber,
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
