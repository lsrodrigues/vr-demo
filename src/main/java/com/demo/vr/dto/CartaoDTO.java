package com.demo.vr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartaoDTO {

    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter 16 dígitos.")
    private String numeroCartao;
    @NotBlank(message = "A senha do cartão é obrigatória.")
    private String senha;


}
