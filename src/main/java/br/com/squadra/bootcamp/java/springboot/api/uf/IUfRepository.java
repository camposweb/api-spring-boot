package br.com.squadra.bootcamp.java.springboot.api.uf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUfRepository extends JpaRepository<UfModel, Long> {

    Boolean existsByCodigoUf(Long codigoUf);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nome);
    
    Boolean existsBySiglaAndCodigoUfNot(String sigla, Long codigoUf);

    Boolean existsByStatus(Integer valor);

    List<UfModel> findByCodigoUf(Long codigoUf);

    List<UfModel> findBySigla(String siglaUf);

    List<UfModel> findByNome(String nomeUf);

    List<UfModel> findByStatus(Integer integer);

    Boolean existsBySiglaAndNome(String sigla,String nome);

    

}
