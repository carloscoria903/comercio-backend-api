package com.comercio_backend_api.repository;

import com.comercio_backend_api.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsByCuit(String cuit);
    boolean existsByDni(String dni);
    Optional<Proveedor> findByCuit(String cuit);
    List<Proveedor> findByEstadoTrue();

}
