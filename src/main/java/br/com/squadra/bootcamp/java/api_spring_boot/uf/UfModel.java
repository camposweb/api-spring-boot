package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_UF")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigoUf")
public class UfModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_UF")
    @SequenceGenerator(name = "SEQUENCE_UF", sequenceName = "SEQUENCE_UF", allocationSize = 1)
    @Column(name = "CODIGO_UF", nullable = false, length = 9)
    private Long codigoUf;

    @Column(name = "SIGLA", nullable = false, length = 3)
    private String sigla;

    @Column(name = "NOME", nullable = false, length = 60)
    private String nome;

    @Column(name = "STATUS", nullable = false, length = 3)
    private int status;

    public UfModel (UfModel dadosUf) {
        this.codigoUf = dadosUf.getCodigoUf();
        this.sigla = dadosUf.getSigla();
        this.nome = dadosUf.getNome();
        this.status = dadosUf.getStatus();
    }
}