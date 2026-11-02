package com.comercio_backend_api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int estado;
    private String mensaje;

    public ErrorResponse(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
}
//Es la estructura sencilla en JSON que se enviará al usuario o al frontend cuando ocurra cualquier error.