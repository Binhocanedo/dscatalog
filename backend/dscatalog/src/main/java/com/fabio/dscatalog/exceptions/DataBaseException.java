package com.fabio.dscatalog.exceptions;

import com.fabio.dscatalog.erros.ErroMensagem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataBaseException extends RuntimeException{
    private final ErroMensagem erroMensagem;
}
