package br.com.squadra.bootcamp.java.springboot.api.uf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUfRepository extends JpaRepository<UfModel, Long>, JpaSpecificationExecutor<UfModel> {

    Boolean existsByCodigoUF(Long codigoUF);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nome);
    
    Boolean existsBySiglaAndCodigoUFNot(String sigla, Long codigoUF);

    Boolean existsBySiglaAndNome(String sigla,String nome);

    Optional<UfModel> findByCodigoUF(Long codigoUF);

    Optional<UfModel> findBySigla(String siglaUf);

    Optional<UfModel> findByNome(String nome);

    Optional<UfModel> findByCodigoUFAndSiglaAndNome(Long codigoUF, String sigla, String nome);

}
