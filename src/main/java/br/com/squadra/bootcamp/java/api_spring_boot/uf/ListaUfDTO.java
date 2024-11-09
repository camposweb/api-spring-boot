package br.com.squadra.bootcamp.java.api_spring_boot.uf;

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
