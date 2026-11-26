package com.comercio_backend_api.service;

import com.comercio_backend_api.dto.ProductoRequestDTO;
import com.comercio_backend_api.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    ProductoResponseDTO crearProducto(ProductoRequestDTO dto);
    List<ProductoResponseDTO> listarActivos();
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO obtenerPorCodigoBarra(String codigoBarra);
    List<ProductoResponseDTO> buscarPorNombre(String nombre);
    ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto);
    void eliminarLogico(Long id);
    ProductoResponseDTO actualizarParcial(Long id, ProductoRequestDTO dto);
    ProductoResponseDTO ingresarStock(Long id, int cantidadIngresada);
}
