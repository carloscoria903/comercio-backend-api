package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductoResponseDTO {

    private Long id;
    private String codigoBarra;
    private String nombre;
    private String descripcion;
    private String marca;
    private BigDecimal precio;
    private int stock;
    private boolean estado;
    private Long categoriaId;
    private String categoriaNombre;

    public ProductoResponseDTO() {
    }

    public ProductoResponseDTO(Long id, String codigoBarra, String nombre, String descripcion, String marca,
                               BigDecimal precio, int stock, boolean estado, String categoriaNombre, Long categoriaId) {
        this.id = id;
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.categoriaNombre = categoriaNombre;
        this.categoriaId = categoriaId;
    }
}
