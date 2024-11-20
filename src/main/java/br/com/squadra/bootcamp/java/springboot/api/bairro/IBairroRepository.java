package br.com.squadra.bootcamp.java.springboot.api.bairro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBairroRepository extends JpaRepository<BairroModel, Long>, JpaSpecificationExecutor<BairroModel> {

    Boolean existsByCodigoBairro(Long codigoBairro);

    Boolean existsByCodigoMunicipio_CodigoMunicipioAndNome(Long codigoMunicipio, String nomes);

    Boolean existsByCodigoMunicipio_CodigoMunicipioAndNomeAndCodigoBairroNot(Long codigoMunicipio, String nome, Long codigoBairro);

}
