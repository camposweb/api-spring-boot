package br.com.squadra.bootcamp.java.springboot.api.bairro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record BairroDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoMunicipio referente")
        Long codigoMunicipio,

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        String nome,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ativo ou 2 para desativado")
        Integer status

) {
}
