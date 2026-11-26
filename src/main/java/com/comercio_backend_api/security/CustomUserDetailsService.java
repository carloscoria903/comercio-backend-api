package com.comercio_backend_api.security;

import com.comercio_backend_api.model.Usuario;
import com.comercio_backend_api.model.enums.Rol;
import com.comercio_backend_api.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario está desactivado.");
        }

        // Si no es ADMIN_SUPER, verificamos que su licencia esté activa y no vencida
        if (usuario.getRol() != Rol.ADMIN_SUPER) {
            if (!usuario.isLicenciaActiva()) {
                throw new RuntimeException("La licencia del comercio está suspendida.");
            }
            if (usuario.getFechaVencimientoLicencia() != null
                    && usuario.getFechaVencimientoLicencia().isBefore(LocalDate.now())) {
                throw new RuntimeException("La licencia del comercio ha vencido el " + usuario.getFechaVencimientoLicencia());
            }
        }

        return new User(
                usuario.getUsuario(),
                usuario.getClave(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
        );
    }
}