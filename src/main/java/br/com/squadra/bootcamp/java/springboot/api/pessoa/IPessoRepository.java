package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPessoRepository extends JpaRepository<PessoaModel, Long>, JpaSpecificationExecutor<PessoaModel> {
}
