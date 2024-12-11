package br.com.squadra.bootcamp.java.springboot.api.municipio;

public record ListaMunicipioDTO(

	Long codigoMunicipio,

	Long codigoUF,

	String nome,

	Integer status

) {

	public ListaMunicipioDTO(MunicipioModel municipioModel) {
		this(
				municipioModel.getCodigoMunicipio(),
				municipioModel.getCodigoUF().getCodigoUF(),
				municipioModel.getNome(),
				municipioModel.getStatus()
		);
	}

}
