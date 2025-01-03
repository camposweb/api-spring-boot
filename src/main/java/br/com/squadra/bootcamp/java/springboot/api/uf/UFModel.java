package br.com.squadra.bootcamp.java.springboot.api.uf;

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
@EqualsAndHashCode(of = "codigoUF")
public class UFModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_UF")
    @SequenceGenerator(name = "SEQUENCE_UF", sequenceName = "SEQUENCE_UF", allocationSize = 1)
    @Column(name = "CODIGO_UF", nullable = false, length = 9)
    private Long codigoUF;

    @Column(name = "SIGLA", nullable = false, length = 3)
    private String sigla;

    @Column(name = "NOME", nullable = false, length = 60)
    private String nome;

    @Column(name = "STATUS", nullable = false, length = 3)
    private Integer status;

    public UFModel (UFDTO dadosUf) {
        this.sigla = dadosUf.sigla();
        this.nome = dadosUf.nome();
        this.status = dadosUf.status();
    }

    public UFModel (Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public void atualizarInformacoes(AtualizacaoUFDTO dadosUf) {
        if (dadosUf.sigla() != null) {
            this.sigla = dadosUf.sigla();
        }
        if (dadosUf.nome() != null) {
            this.nome = dadosUf.nome();
        }
        if (dadosUf.status() != 0) {
            this.status = dadosUf.status();
        }
    }

    public void inativar() {

            this.status = 2;

    }

}