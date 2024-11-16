package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.validacoes.cadastro.ValidadorCadastroUf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UfService {

    @Autowired
    private IUfRepository ufRepository;

    @Autowired
    private List<ValidadorCadastroUf> validadores;

    public List<UfModel> listarTodasUfs() {
        return this.ufRepository.findAll();
    }

    public List<UfModel> listarUfsPorParametros(
        Optional<Long> codigoUf,
        Optional<String> sigla,
        Optional<String> nome,
        Optional<Integer> status
    ) {
        
        if (codigoUf.isPresent()) {
            return this.ufRepository.findByCodigoUf(codigoUf.get());
        } else if (sigla.isPresent()) {
            return this.ufRepository.findBySigla(sigla.get());
        } else if (nome.isPresent()) {
            return this.ufRepository.findByNome(nome.get());
        } else if (status.isPresent()) {
            return this.ufRepository.findByStatus(status.get());
        } else {
            return this.ufRepository.findAll();
        }
    }

    public List<UfModel> cadastrarUf(UfDTO dadosUf) {

        var existeSiglaUf = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (existeSiglaUf) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe", 404);
		}

        var existeNomeUf = this.ufRepository.existsByNome(dadosUf.nome());

        var existeSiglaENome = this.ufRepository.existsBySiglaAndNome(dadosUf.sigla(), dadosUf.nome());

		if (existeSiglaENome) {
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
            throw new ValidacaoException("codigoUf " + dadosUf.codigoUf() + " informado não existe" , 404);
        }

        var atualizarUf = this.ufRepository.getReferenceById(dadosUf.codigoUf());

        var existeSiglaComOutroCodigoUf = this.ufRepository.existsBySiglaAndCodigoUfNot(dadosUf.sigla(), dadosUf.codigoUf());
        if (existeSiglaComOutroCodigoUf) {
            throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe em outra UF", 404);
        }

        atualizarUf.atualizarInformacoes(dadosUf);
        this.ufRepository.save(atualizarUf);

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
