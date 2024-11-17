package br.com.squadra.bootcamp.java.springboot.api.municipio;

import java.util.List;

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

    public List<MunicipioModel> listarMunicipios() {
        return this.municipioRepository.findAll();
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

}
