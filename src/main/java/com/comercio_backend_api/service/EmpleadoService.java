package com.comercio_backend_api.service;

import com.comercio_backend_api.dto.EmpleadoRequestDTO;
import com.comercio_backend_api.dto.EmpleadoResponseDTO;
import java.util.List;

public interface EmpleadoService {

    EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto);
    List<EmpleadoResponseDTO> listarActivos();
    EmpleadoResponseDTO obtenerPorId(Long id);
    EmpleadoResponseDTO obtenerPorLegajo(int legajo);
    EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO dto);
    void darDeBajaEmpleado(Long id);
}
