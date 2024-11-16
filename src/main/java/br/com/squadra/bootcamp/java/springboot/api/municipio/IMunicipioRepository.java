package br.com.squadra.bootcamp.java.springboot.api.municipio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMunicipioRepository extends JpaRepository<MunicipioModel, Long> {

}
