package com.comercio_backend_api.repository;

import com.comercio_backend_api.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByLegajo(int legajo);
    boolean existsByDni(String dni);
    Optional<Empleado> findByLegajo(int legajo);
    List<Empleado> findByEstadoTrue();
}
