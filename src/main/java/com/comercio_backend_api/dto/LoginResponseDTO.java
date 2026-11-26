package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private Long usuarioId;
    private String usuario;
    private String rol;
    private String tipoNegocio;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Long usuarioId, String usuario, String rol, String tipoNegocio) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.rol = rol;
        this.tipoNegocio = tipoNegocio;
    }
}
