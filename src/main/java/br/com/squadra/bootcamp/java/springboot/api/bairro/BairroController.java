package br.com.squadra.bootcamp.java.springboot.api.bairro;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bairro")
public class BairroController {

    @Autowired
    private BairroService bairroService;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoBairro", "codigoMunicipio", "nome", "status");



    @PostMapping
    @Transactional
    public ResponseEntity cadastrarBairro(@RequestBody @Valid BairroDTO dadosBairro) {

        var cadastrarBairro = this.bairroService.cadastrarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(cadastrarBairro);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarBairro(@RequestBody @Valid AtualizacaoBairroDTO dadosBairro) {

        var atualizarBairro = this.bairroService.atualizarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(atualizarBairro);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deletarBairro(@RequestBody @Valid DeletarBairroDTO dadosBairro) {

        var deletarBairro = this.bairroService.deletarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.status(200).body(deletarBairro);
    }

}
