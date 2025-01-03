package br.com.squadra.bootcamp.java.springboot.api.bairro;

import br.com.squadra.bootcamp.java.springboot.api.infra.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Comparator;

@RestController
@RequestMapping("/bairro")
@Tag(name = "bairro", description = "Operações relacionadas a bairros")
public class BairroController {

    @Autowired
    private BairroService bairroService;

    private static final Set<String> PARAMETROS_VALIDOS = Set.of("codigoBairro", "codigoMunicipio", "nome", "status");

    @GetMapping
    public ResponseEntity<?> listarBairros(
            @RequestParam(required = false) Map<String, String> parametros,
            @RequestParam(required = false) Optional<Long> codigoBairro,
            @RequestParam(required = false) Optional<Long> codigoMunicipio,
            @RequestParam(required = false) Optional<String> nome,
            @RequestParam(required = false) Optional<Integer> status
    ) {

        for (String parametro : parametros.keySet()) {
            if (!PARAMETROS_VALIDOS.contains(parametro)) {
                throw new ValidacaoException("Parâmetro " + parametro + " inválido -> Opções codigoMunicipio | codigoMunicipio | nome | status inscritos exatamente como esá descrito ", 404);
            }

        }

        if (status.isPresent() && (status.get() < 1 || status.get() > 2)) {
            throw new ValidacaoException("O parâmetro status aceita somente o valor 1 - ATIVADO ou 2 - DESATIVADO", 404);
        }

        List<BairroModel> listaBairroPorParametros = this.bairroService.listarTodosBairrosPorParametros(codigoBairro, codigoMunicipio, nome, status);
        if (listaBairroPorParametros.size() == 1 && codigoBairro.isPresent()) {
            return ResponseEntity.ok().body(new ListaBairroDTO(listaBairroPorParametros.get(0)));
        }

        List<ListaBairroDTO> listaBairros = listaBairroPorParametros.stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(listaBairros.isEmpty() ? new ArrayList<>() : listaBairros);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<List<ListaBairroDTO>> cadastrarBairro(@RequestBody @Valid BairroDTO dadosBairro) {

        var cadastrarBairro = this.bairroService.cadastrarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(cadastrarBairro);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<List<ListaBairroDTO>> atualizarBairro(@RequestBody @Valid AtualizacaoBairroDTO dadosBairro) {

        var atualizarBairro = this.bairroService.atualizarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.ok().body(atualizarBairro);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<ListaBairroDTO>> deletarBairro(@RequestBody @Valid DeletarBairroDTO dadosBairro) {

        var deletarBairro = this.bairroService.deletarBairro(dadosBairro).stream().map(ListaBairroDTO::new).toList();

        return ResponseEntity.status(200).body(deletarBairro);
    }

}
