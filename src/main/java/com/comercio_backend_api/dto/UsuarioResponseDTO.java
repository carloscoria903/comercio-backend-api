package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.model.enums.TipoNegocio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;
    private String usuario;
    private Boolean activo;
    private Rol rol;
    private TipoNegocio tipoNegocio;
    private LocalDate fechaVencimientoLicencia;
    private Boolean licenciaActiva;
    private Long empleadoId;
    private String nombreCompletoEmpleado;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String usuario, Boolean activo, Rol rol, TipoNegocio tipoNegocio,
                              LocalDate fechaVencimientoLicencia, Boolean licenciaActiva, Long empleadoId,
                              String nombreCompletoEmpleado) {
        this.id = id;
        this.usuario = usuario;
        this.activo = activo;
        this.rol = rol;
        this.tipoNegocio = tipoNegocio;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
        this.licenciaActiva = licenciaActiva;
        this.empleadoId = empleadoId;
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
    }
}
