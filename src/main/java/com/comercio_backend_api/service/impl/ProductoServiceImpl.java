package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.ProductoRequestDTO;
import com.comercio_backend_api.dto.ProductoResponseDTO;
import com.comercio_backend_api.model.Categoria;
import com.comercio_backend_api.model.Producto;
import com.comercio_backend_api.repository.CategoriaRepository;
import com.comercio_backend_api.repository.ProductoRepository;
import com.comercio_backend_api.service.ProductoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        if (productoRepository.existsByCodigoBarra(dto.getCodigoBarra())) {
            throw new RuntimeException("Ya existe un producto con el código de barras: " + dto.getCodigoBarra());
        }

        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la categoría con ID: " + dto.getCategoriaId()));
        }

        Producto producto = new Producto();
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEstado(true);
        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);
        return mapearAResponseDTO(guardado);
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByEstadoTrue()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return mapearAResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO obtenerPorCodigoBarra(String codigoBarra) {
        Producto producto = productoRepository.findByCodigoBarra(codigoBarra)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con código de barras: " + codigoBarra));
        return mapearAResponseDTO(producto);
    }

    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndEstadoTrue(nombre)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        if (!producto.getCodigoBarra().equals(dto.getCodigoBarra())
                && productoRepository.existsByCodigoBarra(dto.getCodigoBarra())) {
            throw new RuntimeException("El código de barras '" + dto.getCodigoBarra() + "' ya pertenece a otro producto.");
        }

        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la categoría con ID: " + dto.getCategoriaId()));
        }

        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public ProductoResponseDTO actualizarParcial(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // 1. Si mandaron un nuevo código de barras
        if (dto.getCodigoBarra() != null && !dto.getCodigoBarra().isBlank()) {
            if (!producto.getCodigoBarra().equals(dto.getCodigoBarra())
                    && productoRepository.existsByCodigoBarra(dto.getCodigoBarra())) {
                throw new RuntimeException("El código de barras '" + dto.getCodigoBarra() + "' ya pertenece a otro producto.");
            }
            producto.setCodigoBarra(dto.getCodigoBarra());
        }

        // 2. Si mandaron un nuevo nombre
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            producto.setNombre(dto.getNombre());
        }

        // 3. Si mandaron una nueva descripción
        if (dto.getDescripcion() != null) {
            producto.setDescripcion(dto.getDescripcion());
        }

        // 4. Si mandaron una nueva marca
        if (dto.getMarca() != null) {
            producto.setMarca(dto.getMarca());
        }

        // 5. Si mandaron un nuevo precio
        if (dto.getPrecio() != null) {
            if (dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El precio debe ser mayor a cero.");
            }
            producto.setPrecio(dto.getPrecio());
        }

        // 6. Si mandaron una nueva categoría
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la categoría con ID: " + dto.getCategoriaId()));
            producto.setCategoria(categoria);
        }

        Producto actualizado = productoRepository.save(producto);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public ProductoResponseDTO ingresarStock(Long id, int cantidadIngresada) {
        if (cantidadIngresada <= 0) {
            throw new RuntimeException("La cantidad a ingresar debe ser mayor a 0.");
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Si estaba en -3 y entran 12, queda en 9 automáticamente
        producto.setStock(producto.getStock() + cantidadIngresada);

        Producto actualizado = productoRepository.save(producto);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public void eliminarLogico(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        producto.setEstado(false);
        productoRepository.save(producto);
    }

    private ProductoResponseDTO mapearAResponseDTO(Producto p) {
        Long catId = (p.getCategoria() != null) ? p.getCategoria().getId() : null;
        String catNombre = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin categoría";

        return new ProductoResponseDTO(
                p.getId(),
                p.getCodigoBarra(),
                p.getNombre(),
                p.getDescripcion(),
                p.getMarca(),
                p.getPrecio(),
                p.getStock(),
                p.isEstado(),
                catNombre,
                catId
        );
    }
}