package com.comercio_backend_api.service;

import com.comercio_backend_api.dto.ProveedorRequestDTO;
import com.comercio_backend_api.dto.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorService {

    ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto);
    List<ProveedorResponseDTO> listarActivos();
    ProveedorResponseDTO obtenerPorId(Long id);
    ProveedorResponseDTO obtenerPorCuit(String cuit);
    ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto);
    void eliminarLogico(Long id);
}
