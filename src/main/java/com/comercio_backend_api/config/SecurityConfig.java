package com.comercio_backend_api.config;

import com.comercio_backend_api.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Login público
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. Gestión de usuarios y licencias: PROHIBIDO para EMPLEADO
                        .requestMatchers("/api/usuarios/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO")

                        // 3. Empleados y Proveedores: PROHIBIDO para EMPLEADO
                        .requestMatchers("/api/empleados/**", "/api/proveedores/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO")

                        // 4. Categorías: EMPLEADO solo puede consultar (GET), no crear/editar/borrar
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO", "EMPLEADO")
                        .requestMatchers("/api/categorias/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO")

                        // 5. Productos:
                        // - Consultar (GET), agregar producto nuevo (POST) e ingresar stock / editar parcial (PATCH): permitido para EMPLEADO
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO", "EMPLEADO")
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO", "EMPLEADO")
                        .requestMatchers(HttpMethod.PATCH, "/api/productos/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO", "EMPLEADO")
                        // - Edición completa (PUT) y Eliminación (DELETE): solo ADMIN_SUPER y CLIENTE_DUEÑO
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO")

                        // 6. Ventas y Clientes: todos los roles logueados pueden operar en caja
                        .requestMatchers("/api/ventas/**", "/api/clientes/**").hasAnyRole("ADMIN_SUPER", "CLIENTE_DUEÑO", "EMPLEADO")

                        // Cualquier otra petición requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}