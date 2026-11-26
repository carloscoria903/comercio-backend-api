package com.comercio_backend_api.controller;

import com.comercio_backend_api.dto.ProductoRequestDTO;
import com.comercio_backend_api.dto.ProductoResponseDTO;
import com.comercio_backend_api.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody ProductoRequestDTO requestDTO) {
        ProductoResponseDTO creado = productoService.crearProducto(requestDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/codigo/{codigoBarra}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorCodigoBarra(@PathVariable String codigoBarra) {
        return ResponseEntity.ok(productoService.obtenerPorCodigoBarra(codigoBarra));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,
                                                          @RequestBody ProductoRequestDTO requestDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, requestDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarParcial(@PathVariable Long id,
                                                                 @RequestBody ProductoRequestDTO requestDTO) {
        return ResponseEntity.ok(productoService.actualizarParcial(id, requestDTO));
    }

    @PatchMapping("/{id}/ingresar-stock")
    public ResponseEntity<ProductoResponseDTO> ingresarStock(@PathVariable Long id,
                                                             @RequestParam int cantidad) {
        return ResponseEntity.ok(productoService.ingresarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
