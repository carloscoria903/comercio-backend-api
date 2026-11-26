package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.FormaPago;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VentaRequestDTO {

    private Long usuarioId;
    private Long clienteId;// Opcional (null en venta rápida de mostrador)
    private FormaPago formaPago;
    private List<DetalleVentaRequestDTO> detalles;

    public VentaRequestDTO() {
    }
}
