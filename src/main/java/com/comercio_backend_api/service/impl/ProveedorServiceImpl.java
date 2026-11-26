package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.ProveedorRequestDTO;
import com.comercio_backend_api.dto.ProveedorResponseDTO;
import com.comercio_backend_api.model.Proveedor;
import com.comercio_backend_api.repository.ProveedorRepository;
import com.comercio_backend_api.service.ProveedorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto) {
        if (dto.getCuit() != null && proveedorRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("Ya existe un proveedor registrado con el CUIT: " + dto.getCuit());
        }
        if (dto.getDni() != null && proveedorRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("Ya existe un proveedor registrado con el DNI: " + dto.getDni());
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setDni(dto.getDni());
        proveedor.setNombre(dto.getNombre());
        proveedor.setApellido(dto.getApellido());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setFechaNacimiento(dto.getFechaNacimiento());
        proveedor.setLocalidad(dto.getLocalidad());
        proveedor.setProvincia(dto.getProvincia());
        proveedor.setSexo(dto.getSexo());
        proveedor.setCuit(dto.getCuit());
        proveedor.setEstado(true);

        Proveedor guardado = proveedorRepository.save(proveedor);
        return mapearAResponseDTO(guardado);
    }

    @Override
    public List<ProveedorResponseDTO> listarActivos() {
        return proveedorRepository.findByEstadoTrue()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        return mapearAResponseDTO(proveedor);
    }

    @Override
    public ProveedorResponseDTO obtenerPorCuit(String cuit) {
        Proveedor proveedor = proveedorRepository.findByCuit(cuit)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con CUIT: " + cuit));
        return mapearAResponseDTO(proveedor);
    }

    @Override
    public ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        if (dto.getCuit() != null && !dto.getCuit().equals(proveedor.getCuit())
                && proveedorRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("El CUIT '" + dto.getCuit() + "' ya pertenece a otro proveedor.");
        }

        proveedor.setDni(dto.getDni());
        proveedor.setNombre(dto.getNombre());
        proveedor.setApellido(dto.getApellido());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setFechaNacimiento(dto.getFechaNacimiento());
        proveedor.setLocalidad(dto.getLocalidad());
        proveedor.setProvincia(dto.getProvincia());
        proveedor.setSexo(dto.getSexo());
        proveedor.setCuit(dto.getCuit());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public void eliminarLogico(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        proveedor.setEstado(false);
        proveedorRepository.save(proveedor);
    }

    private ProveedorResponseDTO mapearAResponseDTO(Proveedor p) {
        return new ProveedorResponseDTO(
                p.getId(),
                p.getDni(),
                p.getNombre(),
                p.getApellido(),
                p.getTelefono(),
                p.getFechaNacimiento(),
                p.getLocalidad(),
                p.getProvincia(),
                p.getSexo(),
                p.isEstado(),
                p.getCuit()
        );
    }
}
