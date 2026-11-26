package com.comercio_backend_api.controller;

import com.comercio_backend_api.dto.EmpleadoRequestDTO;
import com.comercio_backend_api.dto.EmpleadoResponseDTO;
import com.comercio_backend_api.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@RequestBody EmpleadoRequestDTO requestDTO) {
        EmpleadoResponseDTO creado = empleadoService.crearEmpleado(requestDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorLegajo(@PathVariable int legajo) {
        return ResponseEntity.ok(empleadoService.obtenerPorLegajo(legajo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Long id,
                                                          @RequestBody EmpleadoRequestDTO requestDTO) {
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        empleadoService.darDeBajaEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}