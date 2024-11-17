package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.uf.UfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMunicipioRepository extends JpaRepository<MunicipioModel, Long> {

Boolean existsByNome(String nome);

Boolean existsByCodigoUf_CodigoUfAndNome(Long codigoUf, String nome);

Boolean existsByCodigoUf_CodigoUfAndNomeNot(Long codigoUf, String nome);

Optional<MunicipioModel> findByNome(String nome);


}
