package br.com.squadra.bootcamp.java.springboot.api.endereco;

import br.com.squadra.bootcamp.java.springboot.api.bairro.ListaBairroDTO;
import br.com.squadra.bootcamp.java.springboot.api.municipio.ListaMunicipioDTO;
import br.com.squadra.bootcamp.java.springboot.api.uf.ListaUFDTO;

public record DetalhamentoEnderecoDTO(

        Long codigoEndereco,

        Long codigoPessoa,

        Long codigoBairro,

        String nomeRua,

        String numero,

        String complemento,

        String cep,

        ListaBairroDTO bairro,

        ListaMunicipioDTO municipio,

        ListaUFDTO uf

) {
    public DetalhamentoEnderecoDTO(EnderecoModel enderecoModel) {
        this(
                enderecoModel.getCodigoEndereco(),
                enderecoModel.getCodigoPessoa().getCodigoPessoa(),
                enderecoModel.getCodigoBairro().getCodigoBairro(),
                enderecoModel.getNomeRua(),
                enderecoModel.getNumero(),
                enderecoModel.getComplemento(),
                enderecoModel.getCep(),
                new ListaBairroDTO(enderecoModel.getCodigoBairro()),
                new ListaMunicipioDTO(enderecoModel.getCodigoBairro().getCodigoMunicipio()),
                new ListaUFDTO(enderecoModel.getCodigoBairro().getCodigoMunicipio().getCodigoUF())
        );
    }
}
