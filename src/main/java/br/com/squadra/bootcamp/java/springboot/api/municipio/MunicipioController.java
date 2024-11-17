package br.com.squadra.bootcamp.java.springboot.api.municipio;

import java.util.List;

import br.com.squadra.bootcamp.java.springboot.api.uf.ListaUfDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;

	@GetMapping
	public ResponseEntity<List<ListaMunicipioDTO>> getmunicipio() {

		var municipios = this.municipioService.listarMunicipios().stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.ok().body(municipios);
	}

	@PostMapping
	@Transactional
	public ResponseEntity cadastrarMunicipio(@RequestBody @Valid MunicipioDTO dadosMunicipio) {

		var cadastrarMunicipio = this.municipioService.cadastrarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(cadastrarMunicipio);
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizarMunicipio(@RequestBody @Valid AtualizacaoMunicipioDTO dadosMunicipio) {

		var atualizarMunicipio = this.municipioService.atualizarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(atualizarMunicipio);
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity deletarMunicipio(@RequestBody @Valid DeletarMunicipioDTO dadosMunicipio) {

		var deletarMunicipio = this.municipioService.deletarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(deletarMunicipio);
	}

}

