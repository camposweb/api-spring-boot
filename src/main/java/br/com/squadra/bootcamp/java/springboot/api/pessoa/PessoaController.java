package br.com.squadra.bootcamp.java.springboot.api.pessoa;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity listarPessoas() {
        var pessoas = this.pessoaService.listarPessoas().stream().map(ListaPessoaDTO::new).toList();

        return ResponseEntity.ok().body(pessoas);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPessoa(@RequestBody @Valid PessoaDTO dadosPessoa) {

        var cadastrarPessoa = this.pessoaService.cadastrarPessoa(dadosPessoa).stream().map(ListaPessoaDTO::new).toList();

        return ResponseEntity.ok().body(cadastrarPessoa);
    }
}
