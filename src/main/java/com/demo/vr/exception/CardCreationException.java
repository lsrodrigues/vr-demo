package com.demo.vr.exception;

import com.demo.vr.dto.CartaoDTO;
import lombok.Getter;


@Getter
public class CardCreationException extends RuntimeException {
    private CartaoDTO card;

    public CardCreationException(CartaoDTO card) {
        this.card = card;
    }

    public CardCreationException() {}

}
