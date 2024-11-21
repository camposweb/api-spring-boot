package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.endereco.EnderecoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TB_PESSOA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_PESSOA")
    @SequenceGenerator(name = "SEQUENCE_PESSOA", sequenceName = "SEQUENCE_PESSOA", allocationSize = 1)
    @Column(name = "CODIGO_PESSOA", nullable = false, length = 9)
    private Long codigoPessoa;

    @Column(name = "NOME", nullable = false, length = 256)
    private String nome;

    @Column(name = "SOBRENOME", nullable = false, length = 256)
    private String sobrenome;

    @Column(name = "IDADE", nullable = false, length = 3)
    private Integer idade;

    @Column(name = "LOGIN", nullable = false, length = 50)
    private String login;

    @Column(name = "SENHA", nullable = false, length = 50)
    private String senha;

    @Column(name = "STATUS", nullable = false, length = 1)
    private Integer status;

    @OneToMany(mappedBy = "codigoPessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoModel> enderecos;

    public PessoaModel(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public PessoaModel(PessoaDTO dadosPessoa) {
        this.nome = dadosPessoa.nome();
        this.sobrenome = dadosPessoa.sobrenome();
        this.idade = dadosPessoa.idade();
        this.login = dadosPessoa.login();
        this.senha = dadosPessoa.senha();
        this.status = dadosPessoa.status();
        //this.enderecos = new EnderecoModel(dadosPessoa.enderecos().stream().map(dadosEndereco -> new EnderecoModel(dadosEndereco)).toList());
        this.enderecos = dadosPessoa.enderecos().stream().map(dadosEndereco -> new EnderecoModel(this, dadosEndereco)).toList();
    }
    

}