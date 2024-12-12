package br.com.squadra.bootcamp.java.springboot.api.uf;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UFDTO(

        @NotBlank
        @Length(min = 1 ,max = 3, message = "Deve conter no mínimo 1 caractere e no máximo 3 caracterees")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
        String sigla,

        @NotBlank
        @Length(min = 1 ,max = 60, message = "Deve conter no mínimo 1 caractere e no máximo 60 caracterees")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
        String nome,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ATIVADO ou 2 para DESATIVADO")
        @Digits(integer = 1, fraction = 0, message = "Aceita somente 1 (ATIVADO) ou 2(DESATIVADO)")
	@Positive(message = "O status deve ser maior que 0.")
        Integer status
) {
}
