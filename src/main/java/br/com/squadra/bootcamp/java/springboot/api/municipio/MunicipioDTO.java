package br.com.squadra.bootcamp.java.springboot.api.municipio;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record MunicipioDTO(

	@NotNull
	@Range(min = 1, message = "Deve conter o codigoUF referente")
	@Digits(integer = 9, fraction = 0, message = "O valor deve ter no máximo 9 dígitos")
	@Positive(message = "O código UF deve ser maior que 0.")
	Long codigoUF,

	@NotBlank
  	@Length(min = 1 ,max = 256, message = "Deve conter no mínimo 1 caracter e no máximo 256 caracteres")
	@Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$", message = "Deve conter apenas letras")
	String nome,

	@NotNull
  	@Range(min = 1, max = 2, message = "Deve conter 1 para ATIVADO ou 2 para DESATIVADO")
  	@Digits(integer = 1, fraction = 0, message = "Aceita somente 1 (ATIVADO) ou 2(DESATIVADO)") @Positive(message = "O status deve ser maior que 0.")
  	Integer status

) {

}
