package br.com.squadra.bootcamp.java.springboot.api.municipio;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record DeletarMunicipioDTO(

        @NotNull
        @Range(min = 1, message = "Deve conter o codigoMunicipio referente")
        Long codigoMunicipio

) {
}
