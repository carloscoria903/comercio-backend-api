package com.comercio_backend_api.config;

import com.comercio_backend_api.model.Usuario;
import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        // Verifica si ya existe tu usuario administrador para no duplicarlo en cada reinicio
        if (!usuarioRepository.existsByUsuario("admin")) {
            Usuario superAdmin = new Usuario();
            superAdmin.setUsuario("admin");
            superAdmin.setClave("admin1234"); // Luego la encriptaremos con BCrypt
            superAdmin.setActivo(true);
            superAdmin.setRol(Rol.ADMIN_SUPER);
            superAdmin.setTipoNegocio(null);
            superAdmin.setFechaVencimientoLicencia(null);
            superAdmin.setLicenciaActiva(true);
            superAdmin.setEmpleado(null);

            usuarioRepository.save(superAdmin);
            System.out.println(">>> Usuario ADMIN_SUPER creado por defecto: admin / admin1234");
        }
    }
}
