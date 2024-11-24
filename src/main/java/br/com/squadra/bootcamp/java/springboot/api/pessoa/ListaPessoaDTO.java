package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.endereco.EnderecoDTO;

import java.util.Collections;
import java.util.List;

public record ListaPessoaDTO(

        Long codigoPessoa,

        String nome,

        String sobrenome,

        Integer idade,

        String login,

        String senha,

        Integer status,

        List<EnderecoDTO> enderecos

) {

    public ListaPessoaDTO(PessoaModel pessoaModel) {
        this(
                pessoaModel.getCodigoPessoa(),
                pessoaModel.getNome(),
                pessoaModel.getSobrenome(),
                pessoaModel.getIdade(),
                pessoaModel.getLogin(),
                pessoaModel.getSenha(),
                pessoaModel.getStatus(),
                Collections.emptyList()
        );
    }
}
