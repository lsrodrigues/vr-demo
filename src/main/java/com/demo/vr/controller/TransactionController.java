package com.demo.vr.controller;

import com.demo.vr.dto.TransactionCardDTO;
import com.demo.vr.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final CardService cardService;

    @PostMapping(
            value = "/transacao",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String transaction(@Valid @RequestBody TransactionCardDTO transactionCardDTO){
        return cardService.doTransaction(transactionCardDTO);
    }

}
