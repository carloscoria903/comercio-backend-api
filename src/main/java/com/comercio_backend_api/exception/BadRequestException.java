package com.comercio_backend_api.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
//Sirve para errores de lógica de
//negocio (por ejemplo, intentar vender más stock del disponible o registrar un DNI que ya existe).
