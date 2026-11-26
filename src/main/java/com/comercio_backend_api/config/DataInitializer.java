package com.comercio_backend_api.config;

import com.comercio_backend_api.model.Usuario;
import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Usuario superAdmin = usuarioRepository.findByUsuario("admin").orElse(new Usuario());
        superAdmin.setUsuario("admin");
        superAdmin.setClave(passwordEncoder.encode("admin1234"));
        superAdmin.setActivo(true);
        superAdmin.setRol(Rol.ADMIN_SUPER);
        superAdmin.setTipoNegocio(null);
        superAdmin.setFechaVencimientoLicencia(null);
        superAdmin.setLicenciaActiva(true);
        superAdmin.setEmpleado(null);

        usuarioRepository.save(superAdmin);
        System.out.println(">>> Usuario ADMIN_SUPER listo con clave encriptada: admin / admin1234");
    }
}