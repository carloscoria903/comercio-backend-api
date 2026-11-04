package com.comercio_backend_api.controller;

import com.comercio_backend_api.dto.CategoriaRequestDTO;
import com.comercio_backend_api.dto.CategoriaResponseDTO;
import com.comercio_backend_api.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    // Constructor explícito escrito a mano
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Endpoint para registrar una nueva categoría en la base de datos.
     * HTTP Method: POST
     * URL: http://localhost:8080/api/categorias
     */
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO nuevaCategoria = categoriaService.crear(dto);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    /**
     * Endpoint para consultar una categoría por su ID.
     * HTTP Method: GET
     * URL: http://localhost:8080/api/categorias/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    /**
     * Endpoint para listar todas las categorías activas (estado = true).
     * HTTP Method: GET
     * URL: http://localhost:8080/api/categorias
     */
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerTodas() {
        List<CategoriaResponseDTO> categorias = categoriaService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Endpoint para actualizar los datos de una categoría existente.
     * HTTP Method: PUT
     * URL: http://localhost:8080/api/categorias/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO actualizada = categoriaService.actualizar(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Endpoint para cambiar el estado activo/inactivo (baja/alta lógica).
     * HTTP Method: PATCH
     * URL: http://localhost:8080/api/categorias/{id}/estado?activo=true/false
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestParam boolean activo) {
        categoriaService.cambiarEstado(id, activo);
        return ResponseEntity.noContent().build();
    }
}
