package br.com.squadra.bootcamp.java.springboot.api.bairro;

import br.com.squadra.bootcamp.java.springboot.api.municipio.MunicipioModel;
import jakarta.persistence.*;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "TB_BAIRRO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigoBairro")
public class BairroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_BAIRRO")
    @SequenceGenerator(name = "SEQUENCE_BAIRRO", sequenceName = "SEQUENCE_BAIRRO", allocationSize = 1)
    @Column(name = "CODIGO_BAIRRO",  nullable = false, length = 9)
    private Long codigoBairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGO_MUNICIPIO", nullable = false)
    private MunicipioModel codigoMunicipio;

    @Column(name = "NOME", nullable = false, length = 256)
    private String nome;

    @Column(name = "STATUS", nullable = false, length = 3)
    private Integer status;

    public BairroModel (BairroDTO dadosBairro) {
        this.codigoMunicipio = new MunicipioModel(dadosBairro.codigoMunicipio());
        this.nome = dadosBairro.nome();
        this.status = dadosBairro.status();
    }

    public BairroModel (Long codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public void atualizarInformacoes(AtualizacaoBairroDTO dadosBairro) {
        if (dadosBairro.codigoMunicipio() != null) {
            this.codigoMunicipio = new MunicipioModel(dadosBairro.codigoMunicipio());
        }
        if (dadosBairro.nome() != null) {
            this.nome = dadosBairro.nome();
        }
        if (dadosBairro.status() != null) {
            this.status = dadosBairro.status();
        }
    }

}
