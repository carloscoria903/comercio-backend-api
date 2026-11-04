package com.comercio_backend_api.model;
import com.comercio_backend_api.model.enums.Provincia;
import com.comercio_backend_api.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String dni;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false, length = 100)
    private String apellido;

    private String telefono;
    private LocalDate fechaNacimiento;
    private String localidad;

    @Enumerated(EnumType.STRING)
    private Provincia provincia;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(nullable = false)
    private boolean estado = true;

    public Persona(Long id, String dni, String nombre, String apellido,
                   String telefono, LocalDate fechaNacimiento, String localidad,
                   Provincia provincia, Sexo sexo, boolean estado)
    {
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
    }

    public Persona() {
    }

}
