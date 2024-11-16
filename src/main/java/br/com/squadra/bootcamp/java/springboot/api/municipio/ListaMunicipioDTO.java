package br.com.squadra.bootcamp.java.springboot.api.municipio;

public record ListaMunicipioDTO(

	Long codigoMunicipio,

	Long codigoUf,

	String nome,

	Integer status

) {

	public ListaMunicipioDTO(MunicipioModel municipioModel) {
		this(
				municipioModel.getCodigoMunicipio(),
				municipioModel.getCodigoUf().getCodigoUf(),
				municipioModel.getNome(),
				municipioModel.getStatus()
		);
	}

}
