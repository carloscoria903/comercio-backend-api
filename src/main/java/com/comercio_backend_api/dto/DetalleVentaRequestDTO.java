package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaRequestDTO {

    private Long productoId;
    private int cantidad;

    public DetalleVentaRequestDTO() {
    }
}
