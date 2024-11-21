package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;

public record DeletarPessoaDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoPessoa referente")
        @Digits(integer = 9, fraction = 0, message = "O valor deve ter no máximo 9 dígitos")
        @Positive(message = "O código da pessoa deve ser maior que 0.")
        Long codigoPessoa

) {
}
