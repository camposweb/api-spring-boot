package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.endereco.DetalhamentoEnderecoDTO;

import java.util.List;

public record DetalhamentoPessoaDTO(

        Long codigoPessoa,

        String nome,

        String sobrenome,

        Integer idade,

        String login,

        String senha,

        Integer status,

        List<DetalhamentoEnderecoDTO> enderecos

) {
    public DetalhamentoPessoaDTO(PessoaModel pessoaModel) {
        this(
                pessoaModel.getCodigoPessoa(),
                pessoaModel.getNome(),
                pessoaModel.getSobrenome(),
                pessoaModel.getIdade(),
                pessoaModel.getLogin(),
                pessoaModel.getSenha(),
                pessoaModel.getStatus(),
                pessoaModel.getEnderecos().stream().map(DetalhamentoEnderecoDTO::new).toList()
        );
    }
}
