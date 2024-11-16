package br.com.squadra.bootcamp.java.springboot.api.municipio;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MunicipioDTO(

	@NotNull
	@Length(min = 1, message = "Deve conter no mínimo o valor 1")
	Long codigoUf,

	@NotBlank
  @Length(max = 256, message = "Deve conter no máximo 256 caracteres")
	String nome,

	@NotNull
  @Range(min = 1, max = 2, message = "Deve conter 1 para ativo ou 2 para desativado")
	Integer status

) {

}
