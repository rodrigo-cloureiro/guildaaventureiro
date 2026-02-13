package br.com.infnet.guildaaventureiro.advice;

import br.com.infnet.guildaaventureiro.dto.ErrorResponse;
import br.com.infnet.guildaaventureiro.exception.EntidadeNaoLocalizadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntidadeNaoLocalizadaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoLocalizadaException(EntidadeNaoLocalizadaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "Recurso não encontrado",
                        List.of(ex.getMessage())
                ));
    }
}
