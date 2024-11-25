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
            Optional<Long> codigoUf,
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

        if (codigoUf.isPresent()) {
            if (this.municipioRepository.count((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUf").get("codigoUf"), codigoUf.get())) == 0) {
                return Collections.emptyList();
            }
            listaMunicipioParametros = listaMunicipioParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoUf").get("codigoUf"), codigoUf.get()));
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

        var existeCodigoUfTBUF = this.ufRepository.existsByCodigoUf(dadosMunicipio.codigoUf());
        if (!existeCodigoUfTBUF) {
            throw new ValidacaoException("codigoUf " + dadosMunicipio.codigoUf() + " informado não existe", 404);
        }

        var existeMunicipioComCodigoUf = this.municipioRepository.existsByCodigoUf_CodigoUfAndNome(dadosMunicipio.codigoUf(), dadosMunicipio.nome());
        if (existeMunicipioComCodigoUf) {
            throw new ValidacaoException("O Município " + dadosMunicipio.nome() + " com codigoUf " + dadosMunicipio.codigoUf() + " já existe", 404);
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

        var existeCodigoUf = this.ufRepository.existsByCodigoUf(dadosMunicipio.codigoUf());
        if (!existeCodigoUf) {
            throw new ValidacaoException("codigoUf " + dadosMunicipio.codigoUf() + " informado não existe", 404);
        }

        var atualizarMunicipio = this.municipioRepository.getReferenceById(dadosMunicipio.codigoMunicipio());

        var existeMunicipioComCodigoUf = this.municipioRepository.existsByCodigoUf_CodigoUfAndNomeAndCodigoMunicipioNot(dadosMunicipio.codigoUf(), dadosMunicipio.nome(), dadosMunicipio.codigoMunicipio());
        if (existeMunicipioComCodigoUf) {
            throw new ValidacaoException("O Município " + dadosMunicipio.nome() + " com codigoUf " + dadosMunicipio.codigoUf() + " já existe", 404);
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
