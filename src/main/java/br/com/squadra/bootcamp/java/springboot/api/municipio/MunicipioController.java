package br.com.squadra.bootcamp.java.springboot.api.municipio;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/municipio")
@Tag(name = "municipio", description = "Operações relacionadas a Municípios")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;

	private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoMunicipio", "codigoUf", "nome", "status");

	@GetMapping
	public ResponseEntity<List<ListaMunicipioDTO>> listarMunicipios(
			@RequestParam(required = false) Map<String, String> parametros,
			@RequestParam(required = false) Optional<Long> codigoMunicipio,
			@RequestParam(required = false) Optional<Long> codigoUf,
			@RequestParam(required = false) Optional<String> nome,
			@RequestParam(required = false) Optional<Integer> status
	) {

		for (String parametro : parametros.keySet()) {
			if (!PARAMETROS_VALIDOS.contains(parametro)) {
				throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoMunicipio | codigoUf | nome | status inscritos exatamente como esá descrito ", 404);
			}

		}

		if (nome.isPresent() && !nome.get().matches("^[a-zA-ZÀ-ÖØ-öø-ÿÇç ]+$")) {
			throw new ValidacaoException("O parâmetro nome deve conter apenas letras", 404);
		}

		if (status.isPresent() && (status.get() < 1 || status.get() > 2)) {
			throw new ValidacaoException("O parâmetro status aceita somente o valor 1 - ATIVADO ou 2 - DESATIVADO", 404);
		}

		if (codigoMunicipio.isPresent()) {
			Optional<MunicipioModel> municipioEncontrado = municipioService.buscarPorCodigoMunicipio(codigoMunicipio.get());
			List<ListaMunicipioDTO> resultado = municipioEncontrado
					.map(model -> List.of(new ListaMunicipioDTO(model)))
					.orElseGet(ArrayList::new);
			return ResponseEntity.ok().body(resultado);
		}

		List<MunicipioModel> listarMunicipiosPorParametros = this.municipioService.listarTodosMunicipiosPorParametros(codigoMunicipio, codigoUf, nome, status);

		List<ListaMunicipioDTO> listaMunicipios = listarMunicipiosPorParametros.stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.ok().body(listaMunicipios);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<List<ListaMunicipioDTO>> cadastrarMunicipio(@RequestBody @Valid MunicipioDTO dadosMunicipio) {

		var cadastrarMunicipio = this.municipioService.cadastrarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(cadastrarMunicipio);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<List<ListaMunicipioDTO>> atualizarMunicipio(@RequestBody @Valid AtualizacaoMunicipioDTO dadosMunicipio) {

		var atualizarMunicipio = this.municipioService.atualizarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(atualizarMunicipio);
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<List<ListaMunicipioDTO>> deletarMunicipio(@RequestBody @Valid DeletarMunicipioDTO dadosMunicipio) {

		var deletarMunicipio = this.municipioService.deletarMunicipio(dadosMunicipio).stream().map(ListaMunicipioDTO::new).toList();

		return ResponseEntity.status(200).body(deletarMunicipio);
	}

}

