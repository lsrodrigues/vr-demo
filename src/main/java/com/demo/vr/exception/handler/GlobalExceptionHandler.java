package com.demo.vr.exception.handler;

import com.demo.vr.exception.CardCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardCreationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity processErrors(CardCreationException e) {
        var responseBody = new HashMap<>();
        responseBody.put("numeroCartao", e.getCard().getNumeroCartao());
        responseBody.put("senha", e.getCard().getSenha());
        return new ResponseEntity(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
