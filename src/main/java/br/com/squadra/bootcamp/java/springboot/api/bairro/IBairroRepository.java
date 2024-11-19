package br.com.squadra.bootcamp.java.springboot.api.bairro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBairroRepository extends JpaRepository<BairroModel, Long>{

    Boolean existsByCodigoBairro(Long codigoBairro);

    Boolean existsByCodigoMunicipio_CodigoMunicipioAndNome(Long codigoMunicipio, String nomes);
}
