package br.com.squadra.bootcamp.java.springboot.api.uf;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/uf")
@Tag(name = "uf", description = "Operações relacionadas a Ufs")
public class UFController {

    @Autowired
    private UFService ufService;

    @Autowired
    private IUFRepository ufRepository;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoUF", "sigla", "nome", "status");

    @GetMapping
    public ResponseEntity<?> listarUfs(
            @RequestParam(required = false) Map<String, String> parametros,
            @RequestParam(required = false) Optional<Long> codigoUF,
            @RequestParam(required = false) Optional<String> sigla,
            @RequestParam(required = false) Optional<String> nome,
            @RequestParam(required = false) Optional<Integer> status
    ) {

        for (String parametro : parametros.keySet()) {
            if (!PARAMETROS_VALIDOS.contains(parametro)) {
                throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoUF | sigla | nome | status inscritos exatamente como esá descrito ", 404);
            }

        }

        if (sigla.isPresent() && !sigla.get().matches("^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$")) {
            throw new ValidacaoException("O parâmetro silgla deve conter apenas letras", 404);
        }

        if (nome.isPresent() && !nome.get().matches("^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$")) {
            throw new ValidacaoException("O parâmetro nome deve conter apenas letras", 404);
        }

        if (status.isPresent() && (status.get() < 1 || status.get() > 2)) {
            throw new ValidacaoException("O parâmetro status aceita somente o valor 1 - ATIVADO ou 2 - DESATIVADO", 404);
        }

        List<UFModel> listarUfPorParametros = this.ufService.listarTodasUfsPorParametros(codigoUF, sigla, nome, status);
        if (listarUfPorParametros.size() == 1) {
            return ResponseEntity.ok().body(new ListaUFDTO(listarUfPorParametros.get(0)));
        }

        List<ListaUFDTO> listaUfs = listarUfPorParametros.stream().map(ListaUFDTO::new).toList();

        return ResponseEntity.ok().body(listaUfs.isEmpty() ? new ArrayList<>() : listaUfs);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaUFDTO>> cadastrarUF(@RequestBody @Valid UFDTO dadosUf) {

        var cadastrarUF = this.ufService.cadastrarUF(dadosUf).stream().map(ListaUFDTO::new).toList();

        return ResponseEntity.status(200).body(cadastrarUF);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<List<ListaUFDTO>> atualizarUF(@RequestBody @Valid AtualizacaoUFDTO dadosUf) {

        var atualizarUF = this.ufService.atualizarUF(dadosUf).stream().map(ListaUFDTO::new).toList();

        return ResponseEntity.status(200).body(atualizarUF);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<ListaUFDTO>> deletarUF(@RequestBody @Valid DeletarUFDTO dadosUf) {

        if (dadosUf.codigoUF() == null) {
            throw new RuntimeException("Código do UF não informado");
        }

        var deletarUF = this.ufService.deletarUF(dadosUf).stream().map(ListaUFDTO::new).toList();

        return ResponseEntity.status(200).body(deletarUF);
    }

}


