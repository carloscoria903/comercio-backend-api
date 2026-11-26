package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductoRequestDTO {

    private String codigoBarra;
    private String nombre;
    private String descripcion;
    private String marca;
    private BigDecimal precio;
    private int stock;
    private Long categoriaId;

    public ProductoRequestDTO() {
    }
}
