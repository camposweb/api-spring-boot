package br.com.squadra.bootcamp.java.springboot.api.bairro;

public record ListaBairroDTO(

        Long codigoBairro,

        Long codigoMunicipio,

        String nome,

        Integer status

) {
    public ListaBairroDTO(BairroModel bairroModel) {
        this(
                bairroModel.getCodigoBairro(),
                bairroModel.getCodigoMunicipio().getCodigoMunicipio(),
                bairroModel.getNome(),
                bairroModel.getStatus()
        );
    }
}
