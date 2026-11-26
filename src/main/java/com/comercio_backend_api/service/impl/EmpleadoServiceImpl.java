package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.EmpleadoRequestDTO;
import com.comercio_backend_api.dto.EmpleadoResponseDTO;
import com.comercio_backend_api.model.Empleado;
import com.comercio_backend_api.repository.EmpleadoRepository;
import com.comercio_backend_api.repository.UsuarioRepository;
import com.comercio_backend_api.service.EmpleadoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto) {
        if (empleadoRepository.existsByLegajo(dto.getLegajo())) {
            throw new RuntimeException("Ya existe un empleado con el legajo: " + dto.getLegajo());
        }
        if (dto.getDni() != null && empleadoRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("Ya existe un empleado registrado con el DNI: " + dto.getDni());
        }

        Empleado empleado = new Empleado();
        empleado.setDni(dto.getDni());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setTelefono(dto.getTelefono());
        empleado.setFechaNacimiento(dto.getFechaNacimiento());
        empleado.setLocalidad(dto.getLocalidad());
        empleado.setProvincia(dto.getProvincia());
        empleado.setSexo(dto.getSexo());
        empleado.setLegajo(dto.getLegajo());
        empleado.setSueldo(dto.getSueldo());
        empleado.setFechaIngreso(dto.getFechaIngreso() != null ? dto.getFechaIngreso() : LocalDate.now());
        empleado.setFechaEgreso(null);
        empleado.setEstado(true);

        Empleado guardado = empleadoRepository.save(empleado);
        return mapearAResponseDTO(guardado);
    }

    @Override
    public List<EmpleadoResponseDTO> listarActivos() {
        return empleadoRepository.findByEstadoTrue()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponseDTO obtenerPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        return mapearAResponseDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO obtenerPorLegajo(int legajo) {
        Empleado empleado = empleadoRepository.findByLegajo(legajo)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con legajo: " + legajo));
        return mapearAResponseDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        if (empleado.getLegajo() != dto.getLegajo() && empleadoRepository.existsByLegajo(dto.getLegajo())) {
            throw new RuntimeException("El legajo " + dto.getLegajo() + " ya pertenece a otro empleado.");
        }

        empleado.setDni(dto.getDni());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setTelefono(dto.getTelefono());
        empleado.setFechaNacimiento(dto.getFechaNacimiento());
        empleado.setLocalidad(dto.getLocalidad());
        empleado.setProvincia(dto.getProvincia());
        empleado.setSexo(dto.getSexo());
        empleado.setLegajo(dto.getLegajo());
        empleado.setSueldo(dto.getSueldo());
        if (dto.getFechaIngreso() != null) {
            empleado.setFechaIngreso(dto.getFechaIngreso());
        }

        Empleado actualizado = empleadoRepository.save(empleado);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void darDeBajaEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        // 1. Baja lógica y fecha de egreso automática
        empleado.setEstado(false);
        empleado.setFechaEgreso(LocalDate.now());
        empleadoRepository.save(empleado);

        // 2. Si el empleado tenía un usuario creado para entrar al sistema, también lo desactivamos
        usuarioRepository.findByEmpleado_Id(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        });
    }

    private EmpleadoResponseDTO mapearAResponseDTO(Empleado e) {
        return new EmpleadoResponseDTO(
                e.getId(),
                e.getDni(),
                e.getNombre(),
                e.getApellido(),
                e.getTelefono(),
                e.getFechaNacimiento(),
                e.getLocalidad(),
                e.getProvincia(),
                e.getSexo(),
                e.isEstado(),
                e.getLegajo(),
                e.getSueldo(),
                e.getFechaIngreso(),
                e.getFechaEgreso()
        );
    }

}
