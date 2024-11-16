package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.uf.UfModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

	@ManyToOne
	@JoinColumn(name = "CODIGO_UF", nullable = false)
	private UfModel codigoUf;

	@Column(name = "NOME", nullable = false, length = 256)
	private String nome;

	@Column(name = "STATUS", nullable = false, length = 3)
	private Integer status;

}
