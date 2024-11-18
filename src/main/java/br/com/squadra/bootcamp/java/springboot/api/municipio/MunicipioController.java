package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import br.com.squadra.bootcamp.java.springboot.api.uf.UfModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;

	private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoMunicipio", "codigoUf", "nome", "status");

	@GetMapping
	public ResponseEntity listarMunicipiosPorParametros(
			@RequestParam(required = false) Map<String, UfModel> parametros,
			@RequestParam(required = false) Optional<Long> codigoMunicipio,
			@RequestParam(required = false) Optional<Long> codigoUf,
			@RequestParam(required = false) Optional<String> nome,
			@RequestParam(required = false) Optional<Integer> status
	) {

		List<ListaMunicipioDTO> listarTodosMunicipios = this.municipioService.listarTodosMunicipios().stream().map(ListaMunicipioDTO::new).toList();

		for (String parametro : parametros.keySet()) {
			if (!PARAMETROS_VALIDOS.contains(parametro)) {
				throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoMunicipio | codigoUf | nome | status inscritos exatamente como esá descrito ", 404);
			}

			if (codigoMunicipio.isPresent()) {
                var resultado = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).findFirst();

				var resultadoVazio = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList().isEmpty();

				return ResponseEntity.ok().body(resultadoVazio ? new ArrayList<>() : resultado);
			}

			if (codigoUf.isPresent()) {

				var resultado = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList();

				var resultadoVazio = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList().isEmpty();

				return ResponseEntity.ok().body(resultadoVazio ? new ArrayList<>() : resultado);
			}

			if (nome.isPresent()) {
				var somenteTexto = nome.get().matches("^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$");
				if (!somenteTexto) {
					throw new ValidacaoException("O parâmetro nome deve conter apenas letras", 404);
				}
				var resultado = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList();

				var resultadoVazio = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList().isEmpty();

				return ResponseEntity.ok().body(resultadoVazio ? new ArrayList<>() : resultado);
			}

			if (status.isPresent()) {
				if (status.get() < 1 || status.get() > 2) {
					return ResponseEntity.ok().body(new ArrayList<>());
				}

				var resultado = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList();

				var resultadoVazio = this.municipioService.listarMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status).stream().map(ListaMunicipioDTO::new).toList().isEmpty();

				return ResponseEntity.ok().body(resultadoVazio ? new ArrayList<>() : resultado);
			}
		}

		return ResponseEntity.ok().body(listarTodosMunicipios);
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

