package br.com.squadra.bootcamp.java.springboot.api.bairro;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bairro")
public class BairroController {

    @Autowired
    private BairroService bairroService;

    @GetMapping
    public ResponseEntity listarBairros() {
        var bairros = this.bairroService.listarBairros().stream().map(ListaBairroDTO::new).toList();
        return ResponseEntity.ok().body(bairros);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarBairro(@RequestBody @Valid BairroDTO dadosBairro) {

        var cadastrarBairro = this.bairroService.cadastrarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(cadastrarBairro);
    }


}
