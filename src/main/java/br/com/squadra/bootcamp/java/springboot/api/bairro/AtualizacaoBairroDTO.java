package br.com.squadra.bootcamp.java.springboot.api.bairro;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record AtualizacaoBairroDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoMunicipio referente")
        @Digits(integer = 9, fraction = 0, message = "O valor deve ter no máximo 9 dígitos")
        @Positive(message = "O código do bairro deve ser maior que 0.")
        Long codigoBairro,

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoUF referente")
        @Digits(integer = 9, fraction = 0, message = "O valor deve ter no máximo 9 dígitos")
        @Positive(message = "O código do município deve ser maior que 0.")
        Long codigoMunicipio,

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        String nome,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ATIVADO ou 2 para DESATIVADO")
        @Digits(integer = 1, fraction = 0, message = "Aceita somente 1 (ATIVADO) ou 2(DESATIVADO)")
	    @Positive(message = "O status deve ser maior que 0.")
        Integer status

) {
}
