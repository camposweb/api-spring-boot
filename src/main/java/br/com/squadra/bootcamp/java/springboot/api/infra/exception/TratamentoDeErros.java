package br.com.squadra.bootcamp.java.springboot.api.infra.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();


        return ResponseEntity.status(404).body(errors.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        Integer status = ex.getStatus();
        String mensagemErro = ex.getMessage();
        var error = Map.of("mensagem", mensagemErro, "status", status);
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErroCampoInexistente(HttpMessageNotReadableException ex) {

        var campo = "";

        var campoLong = ex.getCause().getMessage().contains("Cannot deserialize value of type `java.lang.Long` from String");
        if (campoLong) {
            campo = "numérico";
        }

        Throwable causa = ex.getCause();
        var mensagemErro = Map.of("mensagem", "A estrutura JSON da requisição está incorreta -> O campo deve ser do tipo " + campo, "status", 404);


        if (causa instanceof UnrecognizedPropertyException e) {
            String campoInexistente = e.getPropertyName();
            mensagemErro = Map.of("mensagem", "O campo '" + campoInexistente + "' não existe nesta estrutura JSON", "status", 404);
        }




        return ResponseEntity.status(404).body(mensagemErro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity tratarMethodArgumentType(MethodArgumentTypeMismatchException ex) {

        String campo = "";

        var campoLong = ex.getCause().getMessage().contains("RequestParam java.lang.Long");
        if (campoLong) {
            campo = "numérico";
        }

        var campoString = ex.getCause().getMessage().contains("RequestParam java.lang.String");
        if (campoString) {
            campo = "texto(String)";
        }

        var campoInteger = ex.getCause().getMessage().contains("RequestParam java.lang.Integer");
        if (campoInteger) {
            campo = "numérico";
        }

        var error = Map.of("mensagem", "O parâmetro " + ex.getParameter().getParameter().getName() + " não é do tipo correto -> O campo deve ser do tipo " + campo, "status", 404);

        return ResponseEntity.status(404).body(error);
    }

    /* @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    } */

    /* @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
    } */

    /* @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    } */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }


    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(),  "O campo " + erro.getField() + " é Obrigatório -> " + erro.getDefaultMessage());
        }
    }
}
