package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pessoa")
@Tag(name = "pessoa", description = "Operações relacionadas a Pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoPessoa", "login", "status");

    @GetMapping
    public ResponseEntity<List<ListaPessoaDTO>> listarPessoas(
            @RequestParam(required = false) Map<String, String> parametros,
            @RequestParam(required = false) Optional<Long> codigoPessoa,
            @RequestParam(required = false) Optional<String> login,
            @RequestParam(required = false) Optional<Integer> status
    ) {

        for (String parametro : parametros.keySet()) {
            if (!PARAMETROS_VALIDOS.contains(parametro)) {
                throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoMunicipio | codigoMunicipio | nome | status inscritos exatamente como esá descrito ", 404);
            }

        }

        if (status.isPresent() && (status.get() < 1 || status.get() > 2)) {
            throw new ValidacaoException("O parâmetro status aceita somente o valor 1 - ATIVADO ou 2 - DESATIVADO", 404);
        }

        if (codigoPessoa.isPresent()) {
            Optional<PessoaModel> pessoaEncontrada = this.pessoaService
                    .buscarPorCodigoPessoa(codigoPessoa.get());

            List<ListaPessoaDTO> resultado = pessoaEncontrada
                    .map(model -> List.of(new ListaPessoaDTO(model)))
                    .orElseGet(ArrayList::new);
            return ResponseEntity.ok().body(resultado);
        }

        List<PessoaModel> listarPessoasPorParametros = this.pessoaService.buscarPessoasComParametrosOpcionais(codigoPessoa, login, status);

        List<ListaPessoaDTO> listaPessoas = listarPessoasPorParametros.stream().map(ListaPessoaDTO::new).toList();

        return ResponseEntity.ok().body(listaPessoas);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaPessoaDTO>> cadastrarPessoa(@RequestBody @Valid PessoaDTO dadosPessoa) {

        var cadastrarPessoa = this.pessoaService.cadastrarPessoa(dadosPessoa).stream().map(ListaPessoaDTO::new).toList();

        return ResponseEntity.ok().body(cadastrarPessoa);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<List<ListaPessoaDTO>> atualizarPessoa(@RequestBody @Valid AtualizacaoPessoaDTO dadosPessoa) {

        var atualizarPessoa = this.pessoaService.atualizarPessoa(dadosPessoa).stream().map(ListaPessoaDTO::new).toList();

        return ResponseEntity.ok().body(atualizarPessoa);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<ListaPessoaDTO>> deletarPessoa(@RequestBody @Valid DeletarPessoaDTO dadosPessoa) {

        var deletarPessoa = this.pessoaService.deletarPessoa(dadosPessoa).stream().map(ListaPessoaDTO::new).toList();
        return ResponseEntity.ok().body(deletarPessoa);
    }
}
