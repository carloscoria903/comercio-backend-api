package com.comercio_backend_api.service;

import com.comercio_backend_api.dto.VentaRequestDTO;
import com.comercio_backend_api.dto.VentaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface VentaService {

    VentaResponseDTO registrarVenta(VentaRequestDTO dto);
    List<VentaResponseDTO> listarTodas();
    VentaResponseDTO obtenerPorId(Long id);
    List<VentaResponseDTO> listarPorFecha(LocalDate desde, LocalDate hasta);
    List<VentaResponseDTO> listarPorUsuario(Long usuarioId);
}
