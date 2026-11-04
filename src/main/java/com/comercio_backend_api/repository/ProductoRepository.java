package com.comercio_backend_api.repository;

import com.comercio_backend_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigoBarra(String codigoBarra);
    Optional<Producto> findByCodigoBarra(String codigoBarra);
    List<Producto> findByEstadoTrue();
    List<Producto> findByCategoriaIdAndEstadoTrue(Long categoriaId);
    List<Producto> findByStockLessThanEqualAndEstadoTrue(Integer stockMinimo);
}
