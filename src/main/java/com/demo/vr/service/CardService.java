package com.demo.vr.service;

import com.demo.vr.converter.CardConverter;
import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.exception.CardCreationException;
import com.demo.vr.exception.CardNotFoundException;
import com.demo.vr.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardConverter cardConverter;

    public CartaoDTO create(CartaoDTO card) {
        cardRepository.findByNumeroCartao(card.getNumeroCartao())
                .ifPresent(c -> {
                    throw new CardCreationException(card);
                });
        cardRepository.save(cardConverter.toDomain(card));
        return card;
    }

    public BigDecimal findBalanceFromCard(String cardNumber) {
        var card = cardRepository.findByNumeroCartao(cardNumber).orElseThrow(CardNotFoundException::new);
        return card.getSaldo();
    }
}
