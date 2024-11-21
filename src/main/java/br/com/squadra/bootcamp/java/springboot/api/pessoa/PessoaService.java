package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.bairro.IBairroRepository;
import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private IBairroRepository bairroRepository;

    public List<PessoaModel> listarPessoas() {
        return this.pessoaRepository.findAll();
    }

    public List<PessoaModel> cadastrarPessoa(PessoaDTO dadosPessoa) {

        var existeLogin = this.pessoaRepository.existsByLogin(dadosPessoa.login());
        if (existeLogin) {
            throw new ValidacaoException("O login " + dadosPessoa.login() + " já existe", 404);
        }

        dadosPessoa.enderecos().forEach(endereco -> {
            var existeCodigoBairro = this.bairroRepository.existsByCodigoBairro(endereco.codigoBairro());
            if (!existeCodigoBairro) {
                throw new ValidacaoException("O Bairro com o código " + endereco.codigoBairro() + " não existe", 404);
            }
        });

        var cadastrarPessoa = new PessoaModel(dadosPessoa);
        this.pessoaRepository.save(cadastrarPessoa);

        return this.pessoaRepository.findAll();
    }
}
