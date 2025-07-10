package com.demo.vr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String password;
    private BigDecimal balance = new BigDecimal(500);

    public Card(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public boolean isCorrectMagicKey(String magicKey) {
        return this.password.equals(magicKey);
    }

    public boolean hasPositiveBalance(BigDecimal value) {
        return this.balance.compareTo(value) >= 0;
    }
}
