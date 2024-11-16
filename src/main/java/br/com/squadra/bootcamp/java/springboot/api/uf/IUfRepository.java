package br.com.squadra.bootcamp.java.springboot.api.uf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUfRepository extends JpaRepository<UfModel, Long> {

    Boolean existsByCodigoUf(Long codigoUf);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nome);
    
    Boolean existsBySiglaAndCodigoUfNot(String sigla, Long codigoUf);

    Boolean existsByStatus(Integer valor);

    Boolean existsByNomeAndStatus(String nome, Integer status);

    List<UfModel> findByCodigoUf(Long codigoUf);

    List<UfModel> findBySigla(String siglaUf);

    List<UfModel> findByNome(String nomeUf);

    List<UfModel> findByStatus(Integer integer);

    Optional<UfModel> findByNomeAndStatus(String nome, Integer status);

    Boolean existsBySiglaAndNome(String sigla,String nome);

    

}
