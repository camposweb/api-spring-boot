package br.com.squadra.bootcamp.java.springboot.api.uf.validacoes.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.IUfRepository;
import br.com.squadra.bootcamp.java.springboot.api.uf.UfDTO;

@Component("ValidadorNomeUfExiste")
public class ValidadorNomeUfExiste {
	
	@Autowired
	private IUfRepository ufRepository;

	public void validar(UfDTO dadosUf) {
		var nomeUfExist = this.ufRepository.existsByNome(dadosUf.nome());
		if (nomeUfExist) {
			throw new ValidacaoException("Nome do UF " + dadosUf.nome() + " já cadastrada", 404);
		}
	}
}
