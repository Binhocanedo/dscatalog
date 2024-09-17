package com.fabio.dscatalog.erros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErroMensagem {

    CATEGORIA_INEXISTENTE("Categoria não encontrada", HttpStatus.NOT_FOUND);

    private final String message;

    private final HttpStatus status;

    public ErroResponse criarErrorResponse(){return new ErroResponse(message, status.name());}
}
