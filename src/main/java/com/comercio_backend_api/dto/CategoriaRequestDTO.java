package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 DTO (Data Transfer Object) para la creación y actualización de Categorías.
 Se utiliza para recibir únicamente los datos necesarios desde el cliente (Postman / Frontend),
 evitando exponer directamente la entidad Categoria de la base de datos.
 **/

@Getter
@Setter
public class CategoriaRequestDTO {

    private String nombre;
    private String descripcion;

    public CategoriaRequestDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoriaRequestDTO() {
    }
}
