package br.com.squadra.bootcamp.java.springboot.api.uf;

public record ListaUfDTO(

        Long codigoUf,

        String sigla,

        String nome,

        int status

) {
    public ListaUfDTO(UfModel ufModel) {
        this(
                ufModel.getCodigoUf(),
                ufModel.getSigla(),
                ufModel.getNome(),
                ufModel.getStatus()
        );
    }
}
