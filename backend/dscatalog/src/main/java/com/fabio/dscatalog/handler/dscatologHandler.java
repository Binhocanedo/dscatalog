package com.fabio.dscatalog.handler;

import com.fabio.dscatalog.erros.ErroMensagem;
import com.fabio.dscatalog.erros.ErroResponse;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.DataBaseException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class dscatologHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroResponse> handleApiException(final ApiException exception, HttpServletRequest request){
        final ErroMensagem erroMensagem = exception.getErroMensage();
        final HttpStatus httpStatus = erroMensagem.getStatus();
        final ErroResponse erroResponse = erroMensagem.criarErrorResponse();

        return ResponseEntity.status(httpStatus).body(erroResponse);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErroResponse> handleDataBaseException(final DataBaseException exception, HttpServletRequest request){
        final ErroMensagem erroMensagem = exception.getErroMensagem();
        final HttpStatus httpStatus = erroMensagem.getStatus();
        final ErroResponse erroResponse = erroMensagem.criarErrorResponse();

        return ResponseEntity.status(httpStatus).body(erroResponse);
    }
}