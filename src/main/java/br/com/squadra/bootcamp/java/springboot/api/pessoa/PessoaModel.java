package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.bairro.BairroModel;
import br.com.squadra.bootcamp.java.springboot.api.endereco.AtualizacaoEnderecoDTO;
import br.com.squadra.bootcamp.java.springboot.api.endereco.EnderecoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "TB_PESSOA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigoPessoa")
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


    public PessoaModel(PessoaDTO dadosPessoa) {
        this.nome = dadosPessoa.nome();
        this.sobrenome = dadosPessoa.sobrenome();
        this.idade = dadosPessoa.idade();
        this.login = dadosPessoa.login();
        this.senha = dadosPessoa.senha();
        this.status = dadosPessoa.status();
        this.enderecos = dadosPessoa.enderecos().stream().map(dadosEndereco -> new EnderecoModel(this, dadosEndereco)).toList();
    }

    public void atualizarInformacoes(AtualizacaoPessoaDTO dadosAtualizacao) {
        // Atualização de informações pessoais
        if (dadosAtualizacao.nome() != null) {
            this.nome = dadosAtualizacao.nome();
        }

        if (dadosAtualizacao.sobrenome() != null) {
            this.sobrenome = dadosAtualizacao.sobrenome();
        }

        if (dadosAtualizacao.idade() != null) {
            this.idade = dadosAtualizacao.idade();
        }

        if (dadosAtualizacao.login() != null) {
            this.login = dadosAtualizacao.login();
        }

        if (dadosAtualizacao.senha() != null) {
            this.senha = dadosAtualizacao.senha();
        }

        if (dadosAtualizacao.status() != null) {
            this.status = dadosAtualizacao.status();
        }


        if (dadosAtualizacao.enderecos() != null) {
            // Separar endereços com codigoEndereco=null
            List<AtualizacaoEnderecoDTO> enderecosSemCodigo = dadosAtualizacao.enderecos().stream()
                    .filter(endereco -> endereco.codigoEndereco() == null)
                    .toList();

            // Agrupar endereços por codigoEndereco, ignorando os que têm codigoEndereco=null
            Map<Long, List<AtualizacaoEnderecoDTO>> enderecosAgrupados = dadosAtualizacao.enderecos().stream()
                    .filter(endereco -> endereco.codigoEndereco() != null)
                    .collect(Collectors.groupingBy(AtualizacaoEnderecoDTO::codigoEndereco));


            Iterator<EnderecoModel> enderecoIterator = this.enderecos.iterator();
            while (enderecoIterator.hasNext()) {
                EnderecoModel enderecoAtual = enderecoIterator.next();


                List<AtualizacaoEnderecoDTO> enderecosDTOs = enderecosAgrupados.get(enderecoAtual.getCodigoEndereco());
                if (enderecosDTOs != null && !enderecosDTOs.isEmpty()) {
                    AtualizacaoEnderecoDTO enderecoDTO = enderecosDTOs.get(0);

                    if (!enderecoDTO.codigoPessoa().equals(this.codigoPessoa)) {
                        throw new IllegalArgumentException("O endereço não pertence a esta pessoa.");
                    }

                    enderecoAtual.setCodigoBairro(new BairroModel(enderecoDTO.codigoBairro()));
                    enderecoAtual.setNomeRua(enderecoDTO.nomeRua());
                    enderecoAtual.setNumero(enderecoDTO.numero());
                    enderecoAtual.setComplemento(enderecoDTO.complemento());
                    enderecoAtual.setCep(enderecoDTO.cep());


                    enderecosDTOs.remove(enderecoDTO);
                    if (enderecosDTOs.isEmpty()) {
                        enderecosAgrupados.remove(enderecoAtual.getCodigoEndereco());
                    }
                } else {
                    enderecoIterator.remove();
                }
            }

            // Adicionar novos endereços com codigoEndereco=null
            for (AtualizacaoEnderecoDTO novoEnderecoDTO : enderecosSemCodigo) {
                if (!novoEnderecoDTO.codigoPessoa().equals(this.codigoPessoa)) {
                    throw new IllegalArgumentException("O endereço não pertence a esta pessoa.");
                }

                EnderecoModel novoEndereco = new EnderecoModel(
                        this,
                        novoEnderecoDTO
                );
                this.enderecos.add(novoEndereco);
            }

            // Adicionar novos endereços com codigoEndereco não nulo
            for (List<AtualizacaoEnderecoDTO> enderecosDTOs : enderecosAgrupados.values()) {
                for (AtualizacaoEnderecoDTO novoEnderecoDTO : enderecosDTOs) {
                    if (!novoEnderecoDTO.codigoPessoa().equals(this.codigoPessoa)) {
                        throw new IllegalArgumentException("O endereço não pertence a esta pessoa.");
                    }

                    EnderecoModel novoEndereco = new EnderecoModel(
                            this,
                            novoEnderecoDTO
                    );
                    this.enderecos.add(novoEndereco);
                }
            }
        }


    }


}