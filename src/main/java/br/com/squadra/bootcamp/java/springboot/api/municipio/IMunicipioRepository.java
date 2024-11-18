package br.com.squadra.bootcamp.java.springboot.api.municipio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMunicipioRepository extends JpaRepository<MunicipioModel, Long> {

    Boolean existsByCodigoMunicipio(Long  codigoMunicipio);

    Boolean existsByCodigoUf_CodigoUfAndNome(Long codigoUf, String nome);

    Boolean existsByCodigoUf_CodigoUfAndNomeAndCodigoMunicipioNot(Long codigoUf, String nome, Long CodigoMunicipio);

    List<MunicipioModel> findByCodigoMunicipio(Long codigoMunicipio);

    List<MunicipioModel> findByCodigoUf_CodigoUf(Long codigoUf);

    List<MunicipioModel> findByNome(String nome);

    List<MunicipioModel> findByStatus(Integer status);

}
