package br.com.squadra.bootcamp.java.springboot.api.uf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUFRepository extends JpaRepository<UFModel, Long>, JpaSpecificationExecutor<UFModel> {

    Boolean existsByCodigoUF(Long codigoUF);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nome);
    
    Boolean existsBySiglaAndCodigoUFNot(String sigla, Long codigoUF);

    Boolean existsBySiglaAndNome(String sigla,String nome);

    Optional<UFModel> findByCodigoUF(Long codigoUF);

    Optional<UFModel> findBySigla(String siglaUf);

    Optional<UFModel> findByNome(String nome);

    Optional<UFModel> findByCodigoUFAndSiglaAndNome(Long codigoUF, String sigla, String nome);

}
