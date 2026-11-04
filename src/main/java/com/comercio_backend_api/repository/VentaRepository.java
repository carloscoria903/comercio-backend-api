package com.comercio_backend_api.repository;

import com.comercio_backend_api.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);
    List<Venta> findByCliente_Id(Long clienteId);
    List<Venta> findByUsuario_Id(Long usuarioId);
}
