package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.CategoriaRequestDTO;
import com.comercio_backend_api.dto.CategoriaResponseDTO;
import com.comercio_backend_api.exception.BadRequestException;
import com.comercio_backend_api.exception.ResourceNotFoundException;
import com.comercio_backend_api.model.Categoria;
import com.comercio_backend_api.repository.CategoriaRepository;
import com.comercio_backend_api.service.CategoriaService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
  Implementación de la lógica de negocio para la gestión de Categorías.
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Constructor clásico para la inyección del repositorio
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe una categoría registrada con el nombre: " + dto.getNombre());
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(true);

        Categoria guardada = categoriaRepository.save(categoria);
        return mapearADTO(guardada);
    }

    @Override
    public CategoriaResponseDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + id));
        return mapearADTO(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> obtenerTodas() {
        List<Categoria> categoriasActivas = categoriaRepository.findByEstadoTrue();
        List<CategoriaResponseDTO> listaDTO = new ArrayList<>();

        for (Categoria c : categoriasActivas) {
            listaDTO.add(mapearADTO(c));
        }

        return listaDTO;
    }

    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + id));

        if (!categoria.getNombre().equalsIgnoreCase(dto.getNombre()) &&
                categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe otra categoría registrada con el nombre: " + dto.getNombre());
        }

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return mapearADTO(actualizada);
    }

    @Override
    public void cambiarEstado(Long id, boolean estado) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + id));
        categoria.setEstado(estado);
        categoriaRepository.save(categoria);
    }


    //Método auxiliar privado para mapear Entidad -> DTO de respuesta.

    private CategoriaResponseDTO mapearADTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.isEstado()
        );
    }
}