package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.uf.UFDTO;
import br.com.squadra.bootcamp.java.springboot.api.uf.UFModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_MUNICIPIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigoMunicipio")
public class MunicipioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_MUNICIPIO")
	@SequenceGenerator(name = "SEQUENCE_MUNICIPIO", sequenceName = "SEQUENCE_MUNICIPIO", allocationSize = 1)
	@Column(name = "CODIGO_MUNICIPIO", nullable = false, length = 9)
	private Long codigoMunicipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_UF", nullable = false)
	private UFModel codigoUF;

	@Column(name = "NOME", nullable = false, length = 256)
	private String nome;

	@Column(name = "STATUS", nullable = false, length = 3)
	private Integer status;

	public MunicipioModel (MunicipioDTO dadosMunicipio) {
		this.codigoUF = new UFModel(dadosMunicipio.codigoUF());
		this.nome = dadosMunicipio.nome();
		this.status = dadosMunicipio.status();
	}

	public MunicipioModel(Long codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public void  atualizarInformacoes(AtualizacaoMunicipioDTO dadosMunicipio) {
		if (dadosMunicipio.codigoUF() != null) {
			this.codigoUF = new UFModel(dadosMunicipio.codigoUF());
		}

		if (dadosMunicipio.nome() != null) {
			this.nome = dadosMunicipio.nome();
		}
		if (dadosMunicipio.status() != 0) {
			this.status = dadosMunicipio.status();
		}
	}
}
