package br.com.squadra.bootcamp.java.springboot.api.uf;

import jakarta.validation.constraints.NotNull;

public record DeletarUfDTO(
        @NotNull
        Long codigoUf
) {
}
