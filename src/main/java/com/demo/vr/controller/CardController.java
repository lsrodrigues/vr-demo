package com.demo.vr.controller;

import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping(
            value = "/cartoes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CartaoDTO createCard(@Valid @RequestBody CartaoDTO card){
        return cardService.create(card);
    }

    @GetMapping(
            value = "/cartoes/{cardNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BigDecimal getBalance(@PathVariable String cardNumber){
        return cardService.findBalanceFromCard(cardNumber);
    }

}
