package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.uf.UfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMunicipioRepository extends JpaRepository<MunicipioModel, Long> {

    Boolean existsByCodigoMunicipio(Long  codigoMunicipio);

    Boolean existsByCodigoUf_CodigoUfAndNome(Long codigoUf, String nome);

    Boolean existsByCodigoUf_CodigoUfAndNomeAndCodigoMunicipioNot(Long codigoUf, String nome, Long CodigoMunicipio);

    Optional<MunicipioModel> findByNome(String nome);

}
