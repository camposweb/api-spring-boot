package br.com.squadra.bootcamp.java.springboot.api.infra.exception;

public class ValidacaoException extends RuntimeException {

    private int status;
    
    public ValidacaoException(String mensagem, int status) {
        super(mensagem);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
}
