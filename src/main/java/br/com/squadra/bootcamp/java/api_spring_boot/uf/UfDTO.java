package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UfDTO(

        @NotBlank
        @Length(max = 9)
        Long codigoUf,

        @NotBlank
        @Length(max = 3)
        String sigla,

        @NotBlank
        @Length(max = 60)
        String nome,

        @NotBlank
        @Length(max = 3)
        int status
) {
}
