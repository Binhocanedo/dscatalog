package com.fabio.dscatalog.exceptions;

import com.fabio.dscatalog.erros.ErroMensagem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@AllArgsConstructor
@FieldNameConstants
public class ApiException extends RuntimeException {
    private final ErroMensagem erroMensage;
}
