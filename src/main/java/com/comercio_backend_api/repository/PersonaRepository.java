package com.comercio_backend_api.repository;

import com.comercio_backend_api.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByDni(String dni);
    Optional<Persona> findByDni(String dni);
}
