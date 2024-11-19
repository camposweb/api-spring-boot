package br.com.squadra.bootcamp.java.springboot.api.bairro;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record DeletarBairroDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoBairro referente")
        Long codigoBairro

) {
}
