package br.com.squadra.bootcamp.java.api_spring_boot.uf.validacoes.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.squadra.bootcamp.java.api_spring_boot.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.api_spring_boot.uf.IUfRepository;
import br.com.squadra.bootcamp.java.api_spring_boot.uf.UfDTO;


@Component("ValidadorSiglaUfExiste")
public class ValidadorSiglaUfExiste implements ValidadorCadastroUf {

	@Autowired
	private IUfRepository ufRepository;

	public void validar(UfDTO dadosUf) {
		var siglaUfExist = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (siglaUfExist) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já cadastrada", 404);
		}
	}
}
