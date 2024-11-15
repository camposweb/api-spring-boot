package br.com.squadra.bootcamp.java.springboot.api.uf;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/uf")
public class UfController {

    @Autowired
    private UfService ufService;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoUf", "sigla", "nome", "status");

    @GetMapping
    public ResponseEntity listarPorParametrosOpcionais(
        @RequestParam(required = false) Map<String, UfModel> parametros,
        @RequestParam(required = false) Optional<Long> codigoUf,
        @RequestParam(required = false) Optional<String> sigla,
        @RequestParam(required = false) Optional<String> nome,
        @RequestParam(required = false) Optional<Integer> status
        ) {

        List<ListaUfDTO> listarTodasUfs = this.ufService.listarTodasUfs().stream().map(ListaUfDTO::new).toList();

            for (String parametro : parametros.keySet()) {
                if (!PARAMETROS_VALIDOS.contains(parametro)) {
                    throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoUf | sigla | nome | status inscritos exatamente como esá descrito ", 404);
                }

                if (codigoUf.isPresent()) {
                    return ResponseEntity.ok().body(this.ufService.listarUfsPorParametros(codigoUf, sigla, nome, status).stream().map(ListaUfDTO::new).findFirst());
                }

                if (sigla.isPresent()) {
                    return ResponseEntity.ok().body(this.ufService.listarUfsPorParametros(codigoUf, sigla, nome, status).stream().map(ListaUfDTO::new).findFirst());
                }

                if (nome.isPresent()) {
                    return ResponseEntity.ok().body(this.ufService.listarUfsPorParametros(codigoUf, sigla, nome, status).stream().map(ListaUfDTO::new).findFirst());
                }

                if ((codigoUf.isPresent() || sigla.isPresent() || nome.isPresent()) && status.isPresent()) {
                    var listaComStatusEOutroParametro = this.ufService.listarUfsPorParametros(codigoUf, sigla, nome, status).stream().map(ListaUfDTO::new).findFirst();
                    return ResponseEntity.ok().body(listaComStatusEOutroParametro);
                }
                
                 listarTodasUfs = this.ufService.listarUfsPorParametros(codigoUf, sigla, nome, status).stream().map(ListaUfDTO::new).toList();
                 
            }

            return ResponseEntity.ok().body(listarTodasUfs);
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


