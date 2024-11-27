package com.vrbeneficios.miniautorizador.integration.controller;

import com.vrbeneficios.miniautorizador.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnCreatedWhenTransactionIsAuthorized() {
        TransactionDTO transactionDTO = new TransactionDTO("6549873025634501", "1234", BigDecimal.valueOf(10.00));

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transacoes",
                transactionDTO,
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenTransactionFailsDueToInvalidPassword() {
        TransactionDTO transactionDTO = new TransactionDTO("6549873025634501", "wrongpassword", BigDecimal.valueOf(10.00));

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transacoes",
                transactionDTO,
                String.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("SENHA_INVALIDA", response.getBody());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenTransactionFailsDueToInsufficientFunds() {
        TransactionDTO transactionDTO = new TransactionDTO("6549873025634501", "1234", BigDecimal.valueOf(600.00));

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transacoes",
                transactionDTO,
                String.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("SALDO_INSUFICIENTE", response.getBody());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenCardDoesNotExist() {
        TransactionDTO transactionDTO = new TransactionDTO("0000000000000000", "1234", BigDecimal.valueOf(10.00));

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transacoes",
                transactionDTO,
                String.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("CARTAO_INEXISTENTE", response.getBody());
    }
}
