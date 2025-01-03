package br.com.squadra.bootcamp.java.springboot.api.endereco;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record EnderecoDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoBairro referente")
        @Digits(integer = 9, fraction = 0, message = "O valor deve ter no máximo 9 dígitos")
        @Positive(message = "O código do bairro deve ser maior que 0.")
        Long codigoBairro,

        @NotBlank
        @Length(min = 1 ,max = 256, message = "Deve conter no mínimo 1 caracteree e no máximo 256 caracteres")
        String nomeRua,

        @NotBlank
        @Length(min = 1 ,max = 10, message = "Deve conter no mínimo 1 caracteree e no máximo 10 caracteres")
        String numero,

        @Length(max = 20, message = "Deve conter no máximo 20 caracteres")
        String complemento,

        @NotBlank
        @Length(max = 10, message = "Deve conter no máximo 10 caracteres")
        String cep

) {
}
