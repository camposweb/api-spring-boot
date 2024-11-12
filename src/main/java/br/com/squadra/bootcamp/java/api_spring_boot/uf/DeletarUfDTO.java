package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import jakarta.validation.constraints.NotNull;

public record DeletarUfDTO(
        @NotNull
        Long codigoUf
) {
}
