package br.com.squadra.bootcamp.java.springboot.api.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends JpaRepository<EnderecoModel, Long>, JpaSpecificationExecutor<EnderecoModel> {
}
