package br.com.squadra.bootcamp.java.springboot.api.municipio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;

	@GetMapping
	public ResponseEntity<List<ListaMunicipioDTO>> getMunicipio() {

		var municipios = this.municipioService.listarMunicipios().stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.ok().body(municipios);
	}


}
