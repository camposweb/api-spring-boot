package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUfRepository extends JpaRepository<UfModel, Long> {

    Boolean existsByCodigoUf(Long codigoUf);

    Boolean existsBySigla(String siglaUf);

    Boolean existsByNome(String nomeUf);

    Optional<UfModel> findByCodigoUf(Long codigoUf);

    Optional<UfModel> findBySigla(String siglaUf);
    
}
