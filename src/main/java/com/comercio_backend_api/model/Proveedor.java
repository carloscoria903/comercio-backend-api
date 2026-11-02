package com.comercio_backend_api.model;
import com.comercio_backend_api.model.enums.Provincia;
import com.comercio_backend_api.model.enums.Sexo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "proveedores")
public class Proveedor extends Persona{

    public Proveedor(Long id, String dni, String nombre, String apellido, String telefono, LocalDate
            fechaNacimiento, String localidad, Provincia provincia, Sexo sexo) {
        super(id, dni, nombre, apellido, telefono, fechaNacimiento, localidad, provincia, sexo);
    }

    public Proveedor() {
    }
}
