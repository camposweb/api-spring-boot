package br.com.squadra.bootcamp.java.springboot.api.bairro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record AtualizacaoBairroDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoMunicipio referente")
        Long codigoBairro,

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoUf referente")
        Long codigoMunicipio,

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
        String nome,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ativo ou 2 para desativado")
        Integer status

) {
}
