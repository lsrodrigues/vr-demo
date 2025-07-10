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
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo = new BigDecimal(500);

    public Card(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public boolean isCorrectMagicKey(String magicKey) {
        return this.senha.equals(magicKey);
    }

    public boolean hasPositiveBalance(BigDecimal value) {
        return this.saldo.compareTo(value) > 0;
    }
}
