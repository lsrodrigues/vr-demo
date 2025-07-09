package com.demo.vr.controller;

import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping(
            value = "/cartoes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CartaoDTO criarCartao(@Valid @RequestBody CartaoDTO card){
        return cardService.create(card);
    }



}
