package br.com.squadra.bootcamp.java.springboot.api.uf;

public record ListaUFDTO(

        Long codigoUF,

        String sigla,

        String nome,

        Integer status

) {
    public ListaUFDTO(UFModel UFModel) {
        this(
                UFModel.getCodigoUF(),
                UFModel.getSigla(),
                UFModel.getNome(),
                UFModel.getStatus()
        );
    }
}
