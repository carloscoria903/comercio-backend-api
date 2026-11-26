package com.comercio_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String usuario;
    private String clave;
}
