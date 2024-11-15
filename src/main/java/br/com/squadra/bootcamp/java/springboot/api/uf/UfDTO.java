package br.com.squadra.bootcamp.java.springboot.api.uf;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UfDTO(

        @NotBlank
        @Length(max = 3, message = "Deve conter no máximo 3 caracteres")
        String sigla,

        @NotBlank
        @Length(max = 60, message = "Deve conter no máximo 60 caracteres")
        String nome,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ativo ou 2 para desativado")
        Integer status
) {
}
