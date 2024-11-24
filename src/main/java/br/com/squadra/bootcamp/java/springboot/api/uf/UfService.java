package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.validacoes.cadastro.ValidadorCadastroUf;
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

    @Autowired
    private List<ValidadorCadastroUf> validadores;

    public List<UfModel> listarTodasUfs() {
        return this.ufRepository.findAll();
    }


    public  Optional<UfModel> buscarPorCodigoUf(Long codigoBairro) {
        return this.ufRepository.findByCodigoUf(codigoBairro);
    }

    public Optional<UfModel> buscarPorSigla(String sigla) {
        return this.ufRepository.findBySigla(sigla);
    }

    public  Optional<UfModel> buscarPorNome(String nome) {
        return this.ufRepository.findByNome(nome);
    }

    public Optional<UfModel> buscarPorCodigoUfSiglaNome(Long codigoUf, String sigla, String nome) {
        return this.ufRepository.findByCodigoUfAndSiglaAndNome(codigoUf, sigla, nome);
    }

    public List<UfModel> listarTodasUfsPorParametros(
        Optional<Long> codigoUf,
        Optional<String> sigla,
        Optional<String> nome,
        Optional<Integer> status
    ) {

        Specification<UfModel> listarUfParametros = Specification.where(null);
        
        if (codigoUf.isPresent()) {
            if (this.ufRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUf"), codigoUf.get())) == 0) {
                return Collections.emptyList();
            }
            listarUfParametros = listarUfParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoBairro"), codigoUf.get()));
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
        //var inativarUf = this.ufRepository.getReferenceById(dadosUf.codigoUf());
        //inativarUf.inativar();


        return this.ufRepository.findAll();
    }
}
