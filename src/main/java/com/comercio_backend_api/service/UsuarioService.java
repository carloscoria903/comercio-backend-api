package com.comercio_backend_api.service;


import com.comercio_backend_api.dto.UsuarioRequestDTO;
import com.comercio_backend_api.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO);
    List<UsuarioResponseDTO> listaUsuario();
    UsuarioResponseDTO obtenerPorId(Long id);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO);
    void eliminarLogico(Long id);
}
