package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.results.graph.collection.internal.BagInitializer;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleVentaResponseDTO {

    private Long id;
    private Long productoId;
    private String productoNombre;
    private String codigoBarra;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleVentaResponseDTO() {
    }

    public DetalleVentaResponseDTO(Long id, Long productoId, String productoNombre,
                                   String codigoBarra, int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.codigoBarra = codigoBarra;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
}
