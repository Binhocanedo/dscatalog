package com.fabio.dscatalog.erros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@AllArgsConstructor
@FieldNameConstants
public class ErroResponse {
    private String message;
    private String status;
}
