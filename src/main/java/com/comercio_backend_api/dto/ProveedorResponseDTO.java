package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.Provincia;
import com.comercio_backend_api.model.enums.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProveedorResponseDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String localidad;
    private Provincia provincia;
    private Sexo sexo;
    private boolean estado;
    private String cuit;

    public ProveedorResponseDTO() {
    }

    public ProveedorResponseDTO(Long id, String dni, String nombre, String apellido, String telefono, LocalDate fechaNacimiento,
                                String localidad, Provincia provincia, Sexo sexo, boolean estado, String cuit) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.localidad = localidad;
        this.provincia = provincia;
        this.sexo = sexo;
        this.estado = estado;
        this.cuit = cuit;
    }
}
