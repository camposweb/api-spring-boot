package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.validacoes.cadastro.ValidadorCadastroUf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        var existeSiglaUf = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (existeSiglaUf) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe", 404);
		}

        var existeNomeUf = this.ufRepository.existsByNome(dadosUf.nome());
		if (existeNomeUf) {
			throw new ValidacaoException("Nome do UF " + dadosUf.nome() + " já existe", 404);
		}

        //validadores.forEach(v -> v.validar(dadosUf));

        var cadastrarUf = new UfModel(dadosUf);
        this.ufRepository.save(cadastrarUf);

        return this.ufRepository.findAll();
    }

    public List<UfModel> atualizarUf(AtualizacaoUfDTO dadosUf) {

        var existeCodigoUf = this.ufRepository.existsByCodigoUf(dadosUf.codigoUf());
        if (!existeCodigoUf) {
            throw new ValidacaoException("UF com o código " + dadosUf.codigoUf() + " não existe", 404);
        }

        var existeSiglaUf = this.ufRepository.existsBySigla(dadosUf.sigla());
        if (!existeSiglaUf) {
            throw new ValidacaoException("Sigla " + dadosUf.sigla() + " não existe", 404);
        }

        var existeNomeUf = this.ufRepository.existsByNome(dadosUf.nome());
        if (!existeNomeUf) {
            throw new ValidacaoException("Nome do UF " + dadosUf.nome() + " já existe", 404);
        }

        var atualizarUf = this.ufRepository.getReferenceById(dadosUf.codigoUf());
        atualizarUf.atualizarInformacoes(dadosUf);

        return this.ufRepository.findAll();
    }

    public List<UfModel> deletarUf(DeletarUfDTO dadosUf) {

        var existeCodigoUf = this.ufRepository.existsByCodigoUf(dadosUf.codigoUf());
        if (!existeCodigoUf) {
            throw new ValidacaoException("UF com o código " + dadosUf.codigoUf() + " não existe", 404);
        }

       this.ufRepository.deleteById(dadosUf.codigoUf());


        return this.ufRepository.findAll();
    }
}
