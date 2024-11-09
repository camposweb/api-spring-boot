package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("uf")
public class UfController {

    @Autowired
    private IUfRepository ufRepository;

    @GetMapping
    public ResponseEntity<ListaUfDTO> listarUf(@RequestParam Long codigoUf) {
        var ufs = this.ufRepository.getReferenceById(codigoUf);
        return ResponseEntity.ok().body(new ListaUfDTO(ufs));
    }
}
