package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 DTO para enviar la respuesta de Categorías al cliente.
 Contiene los datos visibles de la categoría incluyendo su ID y su estado (baja lógica).
 **/

@Getter
@Setter
public class CategoriaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public CategoriaResponseDTO(Long id, String nombre, String descripcion, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public CategoriaResponseDTO() {
    }
}
