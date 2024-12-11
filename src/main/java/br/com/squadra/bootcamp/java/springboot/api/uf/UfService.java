package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UfService {

    @Autowired
    private IUfRepository ufRepository;


    public  Optional<UfModel> buscarPorCodigoUF(Long codigoBairro) {
        return this.ufRepository.findByCodigoUF(codigoBairro);
    }

    public Optional<UfModel> buscarPorSigla(String sigla) {
        return this.ufRepository.findBySigla(sigla);
    }

    public  Optional<UfModel> buscarPorNome(String nome) {
        return this.ufRepository.findByNome(nome);
    }

    public Optional<UfModel> buscarPorCodigoUFSiglaNome(Long codigoUF, String sigla, String nome) {
        return this.ufRepository.findByCodigoUFAndSiglaAndNome(codigoUF, sigla, nome);
    }

    public List<UfModel> listarTodasUfsPorParametros(
        Optional<Long> codigoUF,
        Optional<String> sigla,
        Optional<String> nome,
        Optional<Integer> status
    ) {

        Specification<UfModel> listarUfParametros = Specification.where(null);
        
        if (codigoUF.isPresent()) {
            if (this.ufRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUF"), codigoUF.get())) == 0) {
                return Collections.emptyList();
            }
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoBairro"), codigoUF.get()));
        }

        if (sigla.isPresent()) {
            if (this.ufRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("sigla")), sigla.get().toLowerCase())) == 0) {
                return Collections.emptyList();
            }
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("sigla")), sigla.get().toLowerCase()));
        }

        if (nome.isPresent()) {
            if (this.ufRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nome.get().toLowerCase())) == 0) {
                return Collections.emptyList();
            }
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nome.get().toLowerCase()));
        }

        if (status.isPresent()) {
            if (this.ufRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get())) == 0) {
                return Collections.emptyList();
            }
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get()));
        }

        return this.ufRepository.findAll(listarUfParametros);
    }

    public List<UfModel> cadastrarUf(UfDTO dadosUf) {

        var existeSiglaUf = this.ufRepository.existsBySigla(dadosUf.sigla());
		if (existeSiglaUf) {
			throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe", 404);
		}

        var existeSiglaENome = this.ufRepository.existsBySiglaAndNome(dadosUf.sigla(), dadosUf.nome());

		if (existeSiglaENome) {
			throw new ValidacaoException("Nome do UF " + dadosUf.nome() + " já existe", 404);
		}

        var cadastrarUf = new UfModel(dadosUf);
        this.ufRepository.save(cadastrarUf);

        return this.ufRepository.findAll();
    }

    public List<UfModel> atualizarUf(AtualizacaoUfDTO dadosUf) {

        var existeCodigoUF = this.ufRepository.existsByCodigoUF(dadosUf.codigoUF());
        if (!existeCodigoUF) {
            throw new ValidacaoException("codigoUF " + dadosUf.codigoUF() + " informado não existe" , 404);
        }

        var atualizarUf = this.ufRepository.getReferenceById(dadosUf.codigoUF());

        var existeSiglaComOutroCodigoUF = this.ufRepository.existsBySiglaAndCodigoUFNot(dadosUf.sigla(), dadosUf.codigoUF());
        if (existeSiglaComOutroCodigoUF) {
            throw new ValidacaoException("Sigla " + dadosUf.sigla() + " já existe em outra UF", 404);
        }

        atualizarUf.atualizarInformacoes(dadosUf);
        this.ufRepository.save(atualizarUf);

        return this.ufRepository.findAll();
        
    }

    public List<UfModel> deletarUf(DeletarUfDTO dadosUf) {

        var existeCodigoUF = this.ufRepository.existsByCodigoUF(dadosUf.codigoUF());
        if (!existeCodigoUF) {
            throw new ValidacaoException("UF com o código " + dadosUf.codigoUF() + " não existe", 404);
        }

       this.ufRepository.deleteById(dadosUf.codigoUF());

        return this.ufRepository.findAll();
    }
}
