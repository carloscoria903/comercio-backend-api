package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.model.enums.TipoNegocio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequestDTO {

    private String usuario;
    private String clave;
    private Rol rol;
    private TipoNegocio tipoNegocio;
    private LocalDate fechaVencimientoLicencia;
    private Long empleadoId;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String usuario, String clave, Rol rol, TipoNegocio tipoNegocio,
                             LocalDate fechaVencimientoLiciencia, Long empleadoId) {
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.tipoNegocio = tipoNegocio;
        this.fechaVencimientoLicencia = fechaVencimientoLiciencia;
        this.empleadoId = empleadoId;
    }
}
