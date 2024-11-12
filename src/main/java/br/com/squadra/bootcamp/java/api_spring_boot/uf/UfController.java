package br.com.squadra.bootcamp.java.api_spring_boot.uf;

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
    public ResponseEntity<List<ListaUfDTO>> listarUfs() {

        var ufs = this.ufService.ListarTodasUfs().stream().map(ListaUfDTO::new).toList();

        return ResponseEntity.ok().body(ufs);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaUfDTO>> cadastrarUf(@RequestBody @Valid UfDTO dadosUf) {
       
        var ufs = this.ufService.cadastrarUf(dadosUf).stream().map(ListaUfDTO::new).toList();
        
        return ResponseEntity.ok().body(ufs);
    }

    /* @GetMapping(params = "codigoUf")
    public ResponseEntity listarUf(@RequestParam Long codigoUf) {

        if (codigoUf != null) {
            var ufExist = this.ufRepository.existsByCodigoUf(codigoUf);

            if (ufExist) {
                var uf = this.ufRepository.findByCodigoUf(codigoUf);
                return ResponseEntity.ok().body(uf);
            } else {
                return ResponseEntity.created(null).body("UF Não encontrado");
            }
        }

        var ufs = this.ufRepository.findAll();
        return ResponseEntity.ok().body(ufs);
     */
    
}


