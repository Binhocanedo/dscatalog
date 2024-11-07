package com.fabio.dscatalog.erros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
@FieldNameConstants
public enum ErroMensagem {

    PRODUTO_INEXISTENTE("Produto não encontrado", HttpStatus.NOT_FOUND),
    CATEGORIA_INEXISTENTE("Categoria não encontrada", HttpStatus.NOT_FOUND),
    INTEGRIDADE_VIOLADA("Integridade violada ", HttpStatus.BAD_REQUEST),
    PRODUTO_ATUALIZADO("Já existe produto com essas atualizações", HttpStatus.BAD_REQUEST),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", HttpStatus.NOT_FOUND);


    private final String message;

    private final HttpStatus status;

    public ErroResponse criarErrorResponse(){return new ErroResponse(message, status.name());}
}
