package br.com.squadra.bootcamp.java.springboot.api.municipio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMunicipioRepository extends JpaRepository<MunicipioModel, Long>, JpaSpecificationExecutor<MunicipioModel> {

    Boolean existsByCodigoMunicipio(Long  codigoMunicipio);

    Boolean existsByCodigoUF_CodigoUFAndNome(Long codigoUF, String nome);

    Boolean existsByCodigoUF_CodigoUFAndNomeAndCodigoMunicipioNot(Long codigoUF, String nome, Long CodigoMunicipio);

    Optional<MunicipioModel> findByCodigoMunicipio(Long codigoMunicipio);

}
