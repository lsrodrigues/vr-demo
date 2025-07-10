package com.demo.vr.exception;

import com.demo.vr.dto.CartaoDTO;
import lombok.Getter;


@Getter
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {}

}
