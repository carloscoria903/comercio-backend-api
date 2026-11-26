package com.comercio_backend_api.dto;

import com.comercio_backend_api.model.enums.Provincia;
import com.comercio_backend_api.model.enums.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmpleadoRequestDTO {

    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String localidad;
    private Provincia provincia;
    private Sexo sexo;
    private int legajo;
    private BigDecimal sueldo;
    private LocalDate fechaIngreso;

    public EmpleadoRequestDTO() {
    }
}
