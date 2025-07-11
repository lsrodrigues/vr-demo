package com.test.vr.service;

import com.demo.vr.converter.CardConverter;
import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.dto.TransactionCardDTO;
import com.demo.vr.exception.*;
import com.demo.vr.model.Card;
import com.demo.vr.repository.CardRepository;
import com.demo.vr.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    private CardRepository cardRepository;
    private CardConverter cardConverter;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardRepository = mock(CardRepository.class);
        cardConverter = mock(CardConverter.class);
        cardService = new CardService(cardRepository, cardConverter);
    }

    @Test
    void shouldCreateCardSuccessfully() {
        CartaoDTO dto = new CartaoDTO("123456789", "1234");
        Card card = new Card(1L, "123456789", "1234", new BigDecimal("500.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.empty());
        when(cardConverter.toDomain(dto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);

        CartaoDTO result = cardService.create(dto);

        assertEquals(dto, result);
        verify(cardRepository).save(card);
    }

    @Test
    void shouldThrowCardCreationExceptionWhenCardAlreadyExists() {
        CartaoDTO dto = new CartaoDTO("123456789", "1234");
        Card existingCard = new Card(1L, "123456789", "1234", new BigDecimal("500.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.of(existingCard));

        assertThrows(CardCreationException.class, () -> cardService.create(dto));
        verify(cardRepository, never()).save(any());
    }

    @Test
    void shouldReturnBalanceForExistingCard() {
        Card card = new Card(1L, "123456789", "1234", new BigDecimal("100.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.of(card));

        BigDecimal balance = cardService.findBalanceFromCard("123456789");

        assertEquals(new BigDecimal("100.00"), balance);
    }

    @Test
    void shouldDoTransactionSuccessfully() {
        Card card = new Card(1L, "123456789", "1234", new BigDecimal("100.00"));
        TransactionCardDTO transaction = new TransactionCardDTO("123456789", "1234", new BigDecimal("50.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = cardService.doTransaction(transaction);

        assertEquals("OK", result);
        verify(cardRepository).save(argThat(saved ->
                saved.getBalance().compareTo(new BigDecimal("50.00")) == 0));
    }

    @Test
    void shouldThrowInvalidPasswordException() {
        Card card = new Card(1L, "123456789", "9999", new BigDecimal("100.00"));
        TransactionCardDTO transaction = new TransactionCardDTO("123456789", "1234", new BigDecimal("50.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.of(card));

        assertThrows(InvalidPasswordException.class, () -> cardService.doTransaction(transaction));
    }

    @Test
    void shouldThrowCardHasntSufficientBalanceException() {
        Card card = new Card(1L, "123456789", "1234", new BigDecimal("10.00"));
        TransactionCardDTO transaction = new TransactionCardDTO("123456789", "1234", new BigDecimal("50.00"));

        when(cardRepository.findByCardNumber("123456789")).thenReturn(Optional.of(card));

        assertThrows(CardHasntSufficientBalanceException.class, () -> cardService.doTransaction(transaction));
    }

    @Test
    void shouldThrowCardNotFoundExceptionOnTransaction() {
        TransactionCardDTO transaction = new TransactionCardDTO("999", "1234", new BigDecimal("10.00"));

        when(cardRepository.findByCardNumber("999")).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.doTransaction(transaction));
    }
}
