package com.vrbeneficios.miniautorizador.controller;

import com.vrbeneficios.miniautorizador.dto.TransactionDTO;
import com.vrbeneficios.miniautorizador.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> processTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            transactionService.processTransaction(transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
