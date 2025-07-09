package com.demo.vr.converter;

import com.demo.vr.dto.CartaoDTO;
import com.demo.vr.model.Card;
import org.springframework.stereotype.Component;

@Component
public class CardConverter {

    public CartaoDTO toDTO(Card card){
        return new CartaoDTO(card.getNumeroCartao(), card.getSenha());
    }
    public Card toDomain(CartaoDTO card){
        return new Card(card.getNumeroCartao(), card.getSenha());
    }
}
