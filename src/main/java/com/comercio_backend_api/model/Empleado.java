package com.comercio_backend_api.model;
import com.comercio_backend_api.model.enums.Provincia;
import com.comercio_backend_api.model.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "empleados")
public class Empleado extends Persona{

    @Column(unique = true)
    private int legajo;

    private BigDecimal sueldo;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;

    public Empleado(Long id, String dni, String nombre, String apellido, String telefono, LocalDate fechaNacimiento,
                    String localidad, Provincia provincia, Sexo sexo, int legajo,
                    BigDecimal sueldo, LocalDate fechaIngreso, LocalDate fechaEgreso) {
        super(id, dni, nombre, apellido, telefono, fechaNacimiento, localidad, provincia, sexo);
        this.legajo = legajo;
        this.sueldo = sueldo;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
    }

    public Empleado(){
    }

}
