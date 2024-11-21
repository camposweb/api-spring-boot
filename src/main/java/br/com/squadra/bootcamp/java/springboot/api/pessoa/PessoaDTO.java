package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.endereco.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

public record PessoaDTO(

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
        String nome,

        @NotBlank
        @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
        String sobrenome,

        @NotNull
        @Min(value = 1, message = "Idade deve ser mínimo 1 ano")
        @Max(value = 150, message = "Idade deve ser máximo 150 anos")
        Integer idade,

        @NotBlank
        @Length(max = 50, message = "Deve conter no máximo 50 caracteres")
        String login,

        @NotBlank
        @Length(max = 50, message = "Deve conter no máximo 50 caracteres")
        String senha,

        @NotNull
        @Range(min = 1, max = 2, message = "Deve conter 1 para ativo ou 2 para desativado")
        Integer status,

        @NotEmpty(message = "Deve conter ao menos um endereço")
        @Valid
        List<EnderecoDTO> enderecos

) {
}
