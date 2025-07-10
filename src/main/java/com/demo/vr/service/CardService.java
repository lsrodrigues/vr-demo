package com.demo.vr.service;

import com.demo.vr.converter.CardConverter;
import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.dto.TransactionCardDTO;
import com.demo.vr.exception.CardCreationException;
import com.demo.vr.exception.CardNotFoundException;
import com.demo.vr.exception.InvalidPasswordException;
import com.demo.vr.model.Card;
import com.demo.vr.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

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
       return getCard(cardNumber).getBalance();
    }

    public String doTransaction(TransactionCardDTO transactionCardDTO) {
        var card = getCard(transactionCardDTO.getNumeroCartao());
        validateMagicKey(card, transactionCardDTO.getSenha());
        validateBalance(card, transactionCardDTO.getBalance());


        var remainingBalance = card.getBalance().subtract(transactionCardDTO.getBalance());
        var updatedCard = card.toBuilder()
                .balance(remainingBalance)
                .build();
        cardRepository.save(updatedCard);
        return "OK";
    }

    private Card getCard(String cardNumber) {
        return cardRepository.findByNumeroCartao(cardNumber).orElseThrow(CardNotFoundException::new);
    }

    private void validateMagicKey(Card card, String magicKey) {
        Optional.of(card)
                .filter(c -> c.isCorrectMagicKey(magicKey))
                .orElseThrow(InvalidPasswordException::new);
    }

    private void validateBalance(Card card, BigDecimal value) {
        Optional.of(card)
                .filter(c -> c.hasPositiveBalance(value))
                .orElseThrow(CardHasntSufficientBalanceException::new);
    }
}
