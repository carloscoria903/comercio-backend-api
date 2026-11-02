package com.comercio_backend_api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}

//Sirve para cuando se busca algo que no existe en la base de datos
// (por ejemplo, un cliente, producto o usuario por ID/DNI).