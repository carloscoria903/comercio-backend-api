package com.comercio_backend_api.service;


import com.comercio_backend_api.dto.CategoriaRequestDTO;
import com.comercio_backend_api.dto.CategoriaResponseDTO;

import java.util.List;

/**
 Interfaz que define las operaciones de negocio para la gestión de Categorías (CRUD).
 **/
public interface CategoriaService {
    CategoriaResponseDTO crear(CategoriaRequestDTO dto);
    CategoriaResponseDTO obtenerPorId(Long id);
    List<CategoriaResponseDTO> obtenerTodas();
    CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto);
    void cambiarEstado(Long id, boolean estado);
}
