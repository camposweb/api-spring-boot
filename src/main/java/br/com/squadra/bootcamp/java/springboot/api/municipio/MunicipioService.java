package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.IUfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {

    @Autowired
    private IMunicipioRepository municipioRepository;

    @Autowired
    private IUfRepository ufRepository;


    public Optional<MunicipioModel> buscarPorCodigoMunicipio(Long codigoMunicipio) {
        return this.municipioRepository.findByCodigoMunicipio(codigoMunicipio);
    }

    public List<MunicipioModel> listarTodosMunicipiosPorParametros(
            Optional<Long> codigoMunicipio,
            Optional<Long> codigoUF,
            Optional<String> nome,
            Optional<Integer> status
    ) {

        Specification<MunicipioModel> listaMunicipioParametros = Specification.where(null);

        if (codigoMunicipio.isPresent()) {
            if (this.municipioRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoMunicipio"), codigoMunicipio.get())) == 0) {
                return Collections.emptyList();
            }
            listaMunicipioParametros = listaMunicipioParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoMunicipio"), codigoMunicipio.get()));
        }

        if (codigoUF.isPresent()) {
            if (this.municipioRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUF").get("codigoUF"), codigoUF.get())) == 0) {
                return Collections.emptyList();
            }
            listaMunicipioParametros = listaMunicipioParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUF").get("codigoUF"), codigoUF.get()));
        }

        if (nome.isPresent()) {
            if (this.municipioRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nome.get().toLowerCase())) == 0) {
                return Collections.emptyList();
            }
            listaMunicipioParametros = listaMunicipioParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nome.get().toLowerCase()));
        }

        if (status.isPresent()) {
            if (this.municipioRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get())) == 0) {
                return Collections.emptyList();
            }
            listaMunicipioParametros = listaMunicipioParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get()));
        }

        return this.municipioRepository.findAll(listaMunicipioParametros);
    }

    public List<MunicipioModel> cadastrarMunicipio(MunicipioDTO dadosMunicipio) {

        var existeCodigoUFTBUF = this.ufRepository.existsByCodigoUF(dadosMunicipio.codigoUF());
        if (!existeCodigoUFTBUF) {
            throw new ValidacaoException("codigoUF " + dadosMunicipio.codigoUF() + " informado não existe", 404);
        }

        var existeMunicipioComCodigoUF = this.municipioRepository.existsByCodigoUF_CodigoUFAndNome(dadosMunicipio.codigoUF(), dadosMunicipio.nome());
        if (existeMunicipioComCodigoUF) {
            throw new ValidacaoException("O Município " + dadosMunicipio.nome() + " com codigoUF " + dadosMunicipio.codigoUF() + " já existe", 404);
        }


        var cadastrarMunicipio = new MunicipioModel(dadosMunicipio);
        this.municipioRepository.save(cadastrarMunicipio);

        return this.municipioRepository.findAll();
    }

    public List<MunicipioModel> atualizarMunicipio(AtualizacaoMunicipioDTO dadosMunicipio) {

        var existeCodigoMunicipio = this.municipioRepository.existsByCodigoMunicipio(dadosMunicipio.codigoMunicipio());
        if (!existeCodigoMunicipio) {
            throw new ValidacaoException("codigoMunicipio " + dadosMunicipio.codigoMunicipio() + " informado não existe", 404);
        }

        var existeCodigoUF = this.ufRepository.existsByCodigoUF(dadosMunicipio.codigoUF());
        if (!existeCodigoUF) {
            throw new ValidacaoException("codigoUF " + dadosMunicipio.codigoUF() + " informado não existe", 404);
        }

        var atualizarMunicipio = this.municipioRepository.getReferenceById(dadosMunicipio.codigoMunicipio());

        var existeMunicipioComCodigoUF = this.municipioRepository.existsByCodigoUF_CodigoUFAndNomeAndCodigoMunicipioNot(dadosMunicipio.codigoUF(), dadosMunicipio.nome(), dadosMunicipio.codigoMunicipio());
        if (existeMunicipioComCodigoUF) {
            throw new ValidacaoException("O Município " + dadosMunicipio.nome() + " com codigoUF " + dadosMunicipio.codigoUF() + " já existe", 404);
        }

        atualizarMunicipio.atualizarInformacoes(dadosMunicipio);
        this.municipioRepository.save(atualizarMunicipio);

        return this.municipioRepository.findAll();

    }

    public List<MunicipioModel> deletarMunicipio(DeletarMunicipioDTO dadosMunicipio) {

        var existeCodigoMunicipio = this.municipioRepository.existsByCodigoMunicipio(dadosMunicipio.codigoMunicipio());
        if (!existeCodigoMunicipio) {
            throw new ValidacaoException("Município com o código " + dadosMunicipio.codigoMunicipio() + " não existe", 404);
        }

        this.municipioRepository.deleteById(dadosMunicipio.codigoMunicipio());

        return this.municipioRepository.findAll();
    }

}
