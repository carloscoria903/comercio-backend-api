package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.FormaPago;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VentaResponseDTO {

    private Long id;
    private LocalDateTime fechaHora;
    private Long usuarioId;
    private String usuarioNombre;
    private Long clienteId;
    private String clienteNombreCompleto;
    private FormaPago formaPago;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles;

    public VentaResponseDTO() {
    }

    public VentaResponseDTO(Long id, LocalDateTime fechaHora, Long usuarioId, String usuarioNombre, Long clienteId,
                            String clienteNombreCompleto, FormaPago formaPago, BigDecimal total,
                            List<DetalleVentaResponseDTO> detalles) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.clienteId = clienteId;
        this.clienteNombreCompleto = clienteNombreCompleto;
        this.formaPago = formaPago;
        this.total = total;
        this.detalles = detalles;
    }
}
