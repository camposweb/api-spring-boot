package br.com.squadra.bootcamp.java.springboot.api.uf;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record DeletarUFDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoMunicipio referente")
        Long codigoUF

) {
}
