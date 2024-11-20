package br.com.squadra.bootcamp.java.springboot.api.bairro;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.municipio.IMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BairroService {

    @Autowired
    private IBairroRepository bairroRepository;

    @Autowired
    private IMunicipioRepository municipioRepository;

    public List<BairroModel> listarTodosBairros() {
        return this.bairroRepository.findAll();
    }



    public List<BairroModel> cadastrarBairro(BairroDTO dadosBairro) {

        var existeCodigoMunicipio = this.municipioRepository.existsByCodigoMunicipio(dadosBairro.codigoMunicipio());
        if (!existeCodigoMunicipio) {
            throw new ValidacaoException("Município com o código " + dadosBairro.codigoMunicipio() + " não existe", 404);
        }

        var existeBairroComCodigoMunicipio = this.bairroRepository.existsByCodigoMunicipio_CodigoMunicipioAndNome(dadosBairro.codigoMunicipio(), dadosBairro.nome());
        if (existeBairroComCodigoMunicipio) {
            throw new ValidacaoException("O Bairro " + dadosBairro.nome() + " com codigoMunicipio " + dadosBairro.codigoMunicipio() + " já existe", 404);
        }

        var cadastrarBairro =  new BairroModel(dadosBairro);
        this.bairroRepository.save(cadastrarBairro);

        return this.bairroRepository.findAll();

    }

    public List<BairroModel> atualizarBairro(AtualizacaoBairroDTO dadosBairro) {

        var existeCodigoBairro = this.bairroRepository.existsByCodigoBairro(dadosBairro.codigoBairro());
        if (!existeCodigoBairro) {
            throw new ValidacaoException("codigoBairro " + dadosBairro.codigoBairro() + " informado não existe", 404);
        }

        var existeCodigoMunicipio = this.municipioRepository.existsByCodigoMunicipio(dadosBairro.codigoMunicipio());
        if (!existeCodigoMunicipio) {
            throw new ValidacaoException("codigoMunicipio " + dadosBairro.codigoMunicipio() + " informado não existe", 404);
        }

        var atualizarBairro = this.bairroRepository.getReferenceById(dadosBairro.codigoBairro());

        var existeBairroComCodigoMunicipio = this.bairroRepository.existsByCodigoMunicipio_CodigoMunicipioAndNomeAndCodigoBairroNot(dadosBairro.codigoMunicipio(), dadosBairro.nome(), dadosBairro.codigoBairro());
        if (existeBairroComCodigoMunicipio) {
            throw new ValidacaoException("O Bairro " + dadosBairro.nome() + " com codigoMunicipio " + dadosBairro.codigoMunicipio() + " já existe", 404);
        }

        atualizarBairro.atualizarInformacoes(dadosBairro);
        this.bairroRepository.save(atualizarBairro);

        return this.bairroRepository.findAll();

    }

    public List<BairroModel> deletarBairro(DeletarBairroDTO dadosBairro) {

        var existeCodigoBairro = this.bairroRepository.existsByCodigoBairro(dadosBairro.codigoBairro());
        if (!existeCodigoBairro) {
            throw new ValidacaoException("Bairro com o código " + dadosBairro.codigoBairro() + " não existe", 404);
        }

        this.bairroRepository.deleteById(dadosBairro.codigoBairro());

        return this.bairroRepository.findAll();
    }

}
