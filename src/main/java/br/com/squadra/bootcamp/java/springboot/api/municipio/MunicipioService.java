package br.com.squadra.bootcamp.java.springboot.api.municipio;

import java.util.List;
import java.util.Optional;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.IUfRepository;
import br.com.squadra.bootcamp.java.springboot.api.uf.UfModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipioService {

    @Autowired
    private IMunicipioRepository municipioRepository;

    @Autowired
    private IUfRepository ufRepository;

    public List<MunicipioModel> listarTodosMunicipios() {
        return this.municipioRepository.findAll();
    }

    public List<MunicipioModel> listarMunicipiosPorParametros(
            Optional<Long> codigoMunicipio,
            Optional<Long> codigoUf,
            Optional<String> nome,
            Optional<Integer> status
    ) {

        if (codigoMunicipio.isPresent()) {
            return this.municipioRepository.findByCodigoMunicipio(codigoMunicipio.get());
        } else if (codigoUf.isPresent()) {
            return this.municipioRepository.findByCodigoUf_CodigoUf(codigoUf.get());
        } else if (nome.isPresent()) {
            return this.municipioRepository.findByNome(nome.get());
        } else if (status.isPresent()) {
            return this.municipioRepository.findByStatus(status.get());
        } else {
                return this.municipioRepository.findAll();
            }


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
