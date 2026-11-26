package com.comercio_backend_api.controller;

import com.comercio_backend_api.dto.ProveedorRequestDTO;
import com.comercio_backend_api.dto.ProveedorResponseDTO;
import com.comercio_backend_api.service.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@RequestBody ProveedorRequestDTO requestDTO) {
        ProveedorResponseDTO creado = proveedorService.crearProveedor(requestDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar() {
        return ResponseEntity.ok(proveedorService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorCuit(@PathVariable String cuit) {
        return ResponseEntity.ok(proveedorService.obtenerPorCuit(cuit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Long id,
                                                           @RequestBody ProveedorRequestDTO requestDTO) {
        return ResponseEntity.ok(proveedorService.actualizarProveedor(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
