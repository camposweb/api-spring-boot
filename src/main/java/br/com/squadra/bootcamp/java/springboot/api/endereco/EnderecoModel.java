package br.com.squadra.bootcamp.java.springboot.api.endereco;

import br.com.squadra.bootcamp.java.springboot.api.bairro.BairroModel;
import br.com.squadra.bootcamp.java.springboot.api.pessoa.PessoaModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ENDERECO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigoEndereco")
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_ENDERECO")
    @SequenceGenerator(name = "SEQUENCE_ENDERECO", sequenceName = "SEQUENCE_ENDERECO", allocationSize = 1)
    @Column(name = "CODIGO_ENDERECO", nullable = false, length = 9)
    private Long codigoEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGO_PESSOA", nullable = false)
    private PessoaModel codigoPessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGO_BAIRRO", nullable = false)
    private BairroModel codigoBairro;

    @Column(name = "NOME_RUA", nullable = false, length = 256)
    private String nomeRua;

    @Column(name = "NUMERO", nullable = false, length = 10)
    private String numero;

    @Column(name = "COMPLEMENTO", length = 20)
    private String complemento;

    @Column(name = "CEP", nullable = false, length = 10)
    private String cep;

    public EnderecoModel(PessoaModel pessoa, EnderecoDTO dadosEndereco) {
        this.codigoPessoa = pessoa;
        this.codigoBairro = new BairroModel(dadosEndereco.codigoBairro());
        this.nomeRua = dadosEndereco.nomeRua();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cep = dadosEndereco.cep();
    }

    public EnderecoModel(PessoaModel pessoa, AtualizacaoEnderecoDTO dadosEndereco) {
        this.codigoPessoa = pessoa;
        this.codigoBairro = new BairroModel(dadosEndereco.codigoBairro());
        this.nomeRua = dadosEndereco.nomeRua();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cep = dadosEndereco.cep();
    }

}