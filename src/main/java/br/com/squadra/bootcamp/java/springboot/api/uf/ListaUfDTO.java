package br.com.squadra.bootcamp.java.springboot.api.uf;

public record ListaUfDTO(

        Long codigoUF,

        String sigla,

        String nome,

        Integer status

) {
    public ListaUfDTO(UfModel ufModel) {
        this(
                ufModel.getCodigoUF(),
                ufModel.getSigla(),
                ufModel.getNome(),
                ufModel.getStatus()
        );
    }
}
