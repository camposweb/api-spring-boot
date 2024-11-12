package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.squadra.bootcamp.java.api_spring_boot.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.api_spring_boot.uf.validacoes.cadastro.ValidadorCadastroUf;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Service
public class UfService {

    @Autowired
    private IUfRepository ufRepository;

    @Autowired
    private List<ValidadorCadastroUf> validadores;

    public List<UfModel> ListarTodasUfs() {
        return this.ufRepository.findAll();
    }

    public List<UfModel> cadastrarUf(UfDTO dadosUf) {

        var siglaUfExist = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (siglaUfExist) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já cadastrada", 404);
		}

        var nomeUfExist = this.ufRepository.existsByNome(dadosUf.nome());
		if (nomeUfExist) {
			throw new ValidacaoException("Nome do UF " + dadosUf.nome() + " já cadastrada", 404);
		}
        
        //validadores.forEach(v -> v.validar(dadosUf));

        var ufCreated = new UfModel(dadosUf);
        this.ufRepository.save(ufCreated);

        return this.ufRepository.findAll();
    }




}
