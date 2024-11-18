package br.com.squadra.bootcamp.java.springboot.api.bairro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
