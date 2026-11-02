package com.comercio_backend_api.model;
import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.model.enums.TipoNegocio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String usuario;

    @Column(nullable = false)
    private String clave; // en la carpeta Service se guarda encriptada

    private boolean activo = true;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    //para personalizar el sistema dependiendo qjue cliente tengamos
    @Enumerated(EnumType.STRING)
    private TipoNegocio tipoNegocio;

    //control de couta/ licencia
    private LocalDate fechaVencimientoLicencia;
    private boolean licenciaActiva = true;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona propietario;

    public Usuario(Long id, String usuario, String clave, boolean activo, Rol rol, TipoNegocio tipoNegocio,
                   LocalDate fechaVencimientoLicencia, boolean licenciaActiva, Persona propietario) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.activo = activo;
        this.rol = rol;
        this.tipoNegocio = tipoNegocio;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
        this.licenciaActiva = licenciaActiva;
        this.propietario = propietario;
    }

    public Usuario() {
    }
}
