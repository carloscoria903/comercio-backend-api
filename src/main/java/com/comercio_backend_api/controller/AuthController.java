package com.comercio_backend_api.controller;

import com.comercio_backend_api.dto.LoginRequestDTO;
import com.comercio_backend_api.dto.LoginResponseDTO;
import com.comercio_backend_api.model.Usuario;
import com.comercio_backend_api.repository.UsuarioRepository;
import com.comercio_backend_api.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepository,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getClave())
        );

        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String tipoNegocioStr = usuario.getTipoNegocio() != null ? usuario.getTipoNegocio().name() : null;

        String token = jwtService.generarToken(
                usuario.getUsuario(),
                usuario.getRol().name(),
                usuario.getId(),
                tipoNegocioStr
        );

        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getRol().name(),
                tipoNegocioStr
        ));
    }
}
