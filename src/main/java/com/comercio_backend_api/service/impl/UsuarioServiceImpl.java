package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.UsuarioRequestDTO;
import com.comercio_backend_api.dto.UsuarioResponseDTO;
import com.comercio_backend_api.model.Empleado;
import com.comercio_backend_api.model.Usuario;
import com.comercio_backend_api.repository.EmpleadoRepository;
import com.comercio_backend_api.repository.UsuarioRepository;
import com.comercio_backend_api.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias por constructor
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              EmpleadoRepository empleadoRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsuario(dto.getUsuario())) {
            throw new RuntimeException("El nombre de usuario '" + dto.getUsuario() + "' ya está en uso.");
        }

        Empleado empleado = null;
        if (dto.getEmpleadoId() != null) {
            empleado = empleadoRepository.findById(dto.getEmpleadoId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el empleado con ID: " + dto.getEmpleadoId()));
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setClave(passwordEncoder.encode(dto.getClave()));
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setRol(dto.getRol());
        nuevoUsuario.setTipoNegocio(dto.getTipoNegocio());
        nuevoUsuario.setFechaVencimientoLicencia(dto.getFechaVencimientoLicencia());
        nuevoUsuario.setLicenciaActiva(true);
        nuevoUsuario.setEmpleado(empleado);

        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return mapearAResponseDTO(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listaUsuario() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapearAResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuarioExistente.setUsuario(dto.getUsuario());
        if (dto.getClave() != null && !dto.getClave().isBlank()) {
            usuarioExistente.setClave(passwordEncoder.encode(dto.getClave()));
        }
        usuarioExistente.setRol(dto.getRol());
        usuarioExistente.setTipoNegocio(dto.getTipoNegocio());
        usuarioExistente.setFechaVencimientoLicencia(dto.getFechaVencimientoLicencia());

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public void eliminarLogico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(false);
        usuario.setLicenciaActiva(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDTO mapearAResponseDTO(Usuario u) {
        Long empId = (u.getEmpleado() != null) ? u.getEmpleado().getId() : null;
        String nombreCompleto = (u.getEmpleado() != null)
                ? u.getEmpleado().getNombre() + " " + u.getEmpleado().getApellido()
                : "Administrador del Sistema";

        return new UsuarioResponseDTO(u.getId(),
                u.getUsuario(), u.isActivo(),
                u.getRol(), u.getTipoNegocio(),
                u.getFechaVencimientoLicencia(),
                u.isLicenciaActiva(), empId,
                nombreCompleto);
    }
}