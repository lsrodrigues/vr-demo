package com.demo.vr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TransactionCardDTO {

    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter 16 dígitos.")
    private String numeroCartao;
    @NotBlank(message = "A senha do cartão é obrigatória.")
    private String senha;
    @NotNull(message = "O valor da transação é obrigatório")
    @JsonProperty("valor")
    private BigDecimal balance;


}
