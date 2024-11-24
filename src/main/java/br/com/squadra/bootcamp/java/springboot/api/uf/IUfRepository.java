package br.com.squadra.bootcamp.java.springboot.api.uf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUfRepository extends JpaRepository<UfModel, Long>, JpaSpecificationExecutor<UfModel> {

    Boolean existsByCodigoUf(Long codigoUf);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nome);
    
    Boolean existsBySiglaAndCodigoUfNot(String sigla, Long codigoUf);

    Boolean existsBySiglaAndNome(String sigla,String nome);

    Optional<UfModel> findByCodigoUf(Long codigoUf);

    Optional<UfModel> findBySigla(String siglaUf);

    Optional<UfModel> findByNome(String nome);

    Optional<UfModel> findByCodigoUfAndSiglaAndNome(Long codigoUf, String sigla, String nome);

}
