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
public class UfController {

    @Autowired
    private UfService ufService;

    @Autowired
    private IUfRepository ufRepository;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoUf", "sigla", "nome", "status");

    @GetMapping
    public ResponseEntity listarUfs(
            @RequestParam(required = false) Map<String, UfModel> parametros,
            @RequestParam(required = false) Optional<Long> codigoUf,
            @RequestParam(required = false) Optional<String> sigla,
            @RequestParam(required = false) Optional<String> nome,
            @RequestParam(required = false) Optional<Integer> status
    ) {

        for (String parametro : parametros.keySet()) {
            if (!PARAMETROS_VALIDOS.contains(parametro)) {
                throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoUf | sigla | nome | status inscritos exatamente como esá descrito ", 404);
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

        if (codigoUf.isPresent()) {
            Optional<UfModel> codigoUfEncontrado = this.ufService.buscarPorCodigoUf(codigoUf.get());

            if (codigoUfEncontrado.isEmpty()) {
                return ResponseEntity.ok().body(Collections.emptyList());
            }

            return ResponseEntity.ok().body(new ListaUfDTO(codigoUfEncontrado.get()));
        }

        if (sigla.isPresent()) {
            Optional<UfModel> siglaEncontrada = this.ufService.buscarPorSigla(sigla.get());

            if (siglaEncontrada.isEmpty()) {
                return ResponseEntity.ok().body(Collections.emptyList());
            }

            return ResponseEntity.ok().body(new ListaUfDTO(siglaEncontrada.get()));
        }

        if (nome.isPresent()) {
            Optional<UfModel> nomeEncontrado = this.ufService.buscarPorNome(nome.get());

            if (nomeEncontrado.isEmpty()) {
                return ResponseEntity.ok().body(Collections.emptyList());
            }

            return ResponseEntity.ok().body(new ListaUfDTO(nomeEncontrado.get()));
        }



        List<UfModel> listarUfPorParametros = this.ufService.listarTodasUfsPorParametros(codigoUf, sigla, nome, status);

        List<ListaUfDTO> listaUfs = listarUfPorParametros.stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.ok().body(listaUfs.isEmpty() ? new ArrayList<>() : listaUfs);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> cadastrarUf(@RequestBody @Valid UfDTO dadosUf) {

        var cadastrarUf = this.ufService.cadastrarUf(dadosUf).stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(cadastrarUf);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> atualizarUf(@RequestBody @Valid AtualizacaoUfDTO dadosUf) {

        var atualizarUf = this.ufService.atualizarUf(dadosUf).stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(atualizarUf);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> deletarUf(@RequestBody @Valid DeletarUfDTO dadosUf) {

        if (dadosUf.codigoUf() == null) {
            throw new RuntimeException("Código do UF não informado");
        }

        var deletarUf = this.ufService.deletarUf(dadosUf).stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(deletarUf);
    }

}


