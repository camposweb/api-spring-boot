package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.bairro.IBairroRepository;
import br.com.squadra.bootcamp.java.springboot.api.endereco.IEnderecoRepository;
import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private IBairroRepository bairroRepository;

    public List<PessoaModel> listarPessoas() {
        return this.pessoaRepository.findAll();
    }

    public Optional<PessoaModel> buscarPorCodigoPessoa(Long codigoBairro) {
        return this.pessoaRepository.findByCodigoPessoa(codigoBairro);
    }

    public List<PessoaModel> buscarPessoasComParametrosOpcionais(
            Optional<Long> codigoPessoa,
            Optional<String> login,
            Optional<Integer> status
    ) {

        Specification<PessoaModel> buscarPessoaComParametros = Specification.where(null);

        if (codigoPessoa.isPresent()) {
            buscarPessoaComParametros = buscarPessoaComParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigoPessoa"), codigoPessoa.get()));
        }

        if (login.isPresent()) {
            buscarPessoaComParametros = buscarPessoaComParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("login"), login.get()));
        }

        if (status.isPresent()) {
            buscarPessoaComParametros = buscarPessoaComParametros.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status.get()));
        }

        List<PessoaModel> resultado = this.pessoaRepository.findAll(buscarPessoaComParametros);

        return resultado.isEmpty() ? Collections.emptyList() : resultado;
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


    public List<PessoaModel> atualizarPessoa(AtualizacaoPessoaDTO dadosPessoa) {

        var existeCodigoPessoa = this.pessoaRepository.existsByCodigoPessoa(dadosPessoa.codigoPessoa());
        if (!existeCodigoPessoa) {
            throw new ValidacaoException("A pessoa com o código " + dadosPessoa.codigoPessoa() + " não existe", 404);
        }

        var atualizarPessoa = this.pessoaRepository.getReferenceById(dadosPessoa.codigoPessoa());

        var existeCodigoPessoaComLogin = this.pessoaRepository.existsByLoginAndCodigoPessoaNot(dadosPessoa.login(), dadosPessoa.codigoPessoa());
        if (existeCodigoPessoaComLogin) {
            throw new ValidacaoException("O login " + dadosPessoa.login() + " já existe", 404);
        }

        dadosPessoa.enderecos().forEach(endereco -> {
            var existeCodigoBairro = this.bairroRepository.existsByCodigoBairro(endereco.codigoBairro());
            if (!existeCodigoBairro) {
                throw new ValidacaoException("O Bairro com o código " + endereco.codigoBairro() + " não existe", 404);
            }


        });


        atualizarPessoa.atualizarInformacoes(dadosPessoa);
        this.pessoaRepository.save(atualizarPessoa);


        return this.pessoaRepository.findAll();
    }

    public List<PessoaModel> deletarPessoa(DeletarPessoaDTO dadosPessoa) {

        var existePessoa = this.pessoaRepository.existsByCodigoPessoa(dadosPessoa.codigoPessoa());
        if (!existePessoa) {
            throw new ValidacaoException("A pessoa com o código " + dadosPessoa.codigoPessoa() + " não existe", 404);
        }


        this.pessoaRepository.deleteById(dadosPessoa.codigoPessoa());

        return this.pessoaRepository.findAll();
    }

}
