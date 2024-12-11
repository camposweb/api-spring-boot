package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UFService {

    @Autowired
    private IUFRepository ufRepository;


    public  Optional<UFModel> buscarPorCodigoUF(Long codigoBairro) {
        return this.ufRepository.findByCodigoUF(codigoBairro);
    }

    public Optional<UFModel> buscarPorSigla(String sigla) {
        return this.ufRepository.findBySigla(sigla);
    }

    public  Optional<UFModel> buscarPorNome(String nome) {
        return this.ufRepository.findByNome(nome);
    }

    public Optional<UFModel> buscarPorCodigoUFSiglaNome(Long codigoUF, String sigla, String nome) {
        return this.ufRepository.findByCodigoUFAndSiglaAndNome(codigoUF, sigla, nome);
    }

    public List<UFModel> listarTodasUfsPorParametros(
            Optional<Long> codigoUF,
            Optional<String> sigla,
            Optional<String> nome,
            Optional<Integer> status
    ) {

        Specification<UFModel> listarUfParametros = Specification.where(null);

        if (codigoUF.isPresent()) {
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUF"), codigoUF.get()));
        }

        if (sigla.isPresent()) {
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("sigla")), sigla.get().toLowerCase()));
        }

        if (nome.isPresent()) {
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nome.get().toLowerCase()));
        }

        if (status.isPresent()) {
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get()));
        }

        List<UFModel> resultado = this.ufRepository.findAll(listarUfParametros);

        return resultado.isEmpty() ? new ArrayList<>() : resultado;
    }


    public List<UFModel> cadastrarUF(UFDTO dadosUf) {

        var existeSiglaUf = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (existeSiglaUf) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe", 404);
		}

        var existeSiglaENome = this.ufRepository.existsBySiglaAndNome(dadosUf.sigla(), dadosUf.nome());

		if (existeSiglaENome) {
			throw new ValidacaoException("Nome da UF " + dadosUf.nome() + " já existe", 404);
		}

        var cadastrarUF = new UFModel(dadosUf);
        this.ufRepository.save(cadastrarUF);

        return this.ufRepository.findAll();
    }

    public List<UFModel> atualizarUF(AtualizacaoUFDTO dadosUf) {

        var existeCodigoUF = this.ufRepository.existsByCodigoUF(dadosUf.codigoUF());
        if (!existeCodigoUF) {
            throw new ValidacaoException("codigoUF " + dadosUf.codigoUF() + " informado não existe" , 404);
        }

        var atualizarUF = this.ufRepository.getReferenceById(dadosUf.codigoUF());

        var existeSiglaComOutroCodigoUF = this.ufRepository.existsBySiglaAndCodigoUFNot(dadosUf.sigla(), dadosUf.codigoUF());
        if (existeSiglaComOutroCodigoUF) {
            throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe em outra UF", 404);
        }

        atualizarUF.atualizarInformacoes(dadosUf);
        this.ufRepository.save(atualizarUF);

        return this.ufRepository.findAll();
        
    }

    public List<UFModel> deletarUF(DeletarUFDTO dadosUf) {

        var existeCodigoUF = this.ufRepository.existsByCodigoUF(dadosUf.codigoUF());
        if (!existeCodigoUF) {
            throw new ValidacaoException("UF com o código " + dadosUf.codigoUF() + " não existe", 404);
        }

       this.ufRepository.deleteById(dadosUf.codigoUF());

        return this.ufRepository.findAll();
    }
}
