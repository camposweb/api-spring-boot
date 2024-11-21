package br.com.squadra.bootcamp.java.springboot.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record EnderecoDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoBairro referente")
        Long codigoBairro,

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        String nomeRua,

        @NotBlank
        @Length(max = 10, message = "Deve conter no máximo 10 caracteres")
        String numero,

        @NotBlank
        @Length(max = 20, message = "Deve conter no máximo 20 caracteres")
        String complemento,

        @NotBlank
        @Length(max = 10, message = "Deve conter no máximo 10 caracteres")
        String cep

) {
}
