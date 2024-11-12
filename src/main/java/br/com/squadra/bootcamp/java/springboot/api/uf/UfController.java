package br.com.squadra.bootcamp.java.springboot.api.uf;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/uf")
public class UfController {

    @Autowired
    private UfService ufService;

    @GetMapping
    public ResponseEntity<List<ListaUfDTO>> listarUf() {

        var ufs = this.ufService.ListarTodasUfs().stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(ufs);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> cadastrarUf(@RequestBody @Valid UfDTO dadosUf) {

        var ufs = this.ufService.cadastrarUf(dadosUf).stream().map(ListaUfDTO::new).toList();
        
        return ResponseEntity.status(200).body(ufs);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> atualizarUf(@RequestBody @Valid AtualizacaoUfDTO dadosUf) {

        var ufs = this.ufService.atualizarUf(dadosUf).stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(ufs);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> deletarUf(@RequestBody @Valid DeletarUfDTO dadosUf) {

        if (dadosUf.codigoUf() == null) {
            throw new RuntimeException("Código do UF não informado");
        }

        var ufs = this.ufService.deletarUf(dadosUf).stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.status(200).body(ufs);
    }

}


