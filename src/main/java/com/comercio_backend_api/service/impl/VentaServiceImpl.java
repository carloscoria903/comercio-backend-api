package com.comercio_backend_api.service.impl;

import com.comercio_backend_api.dto.DetalleVentaRequestDTO;
import com.comercio_backend_api.dto.DetalleVentaResponseDTO;
import com.comercio_backend_api.dto.VentaRequestDTO;
import com.comercio_backend_api.dto.VentaResponseDTO;
import com.comercio_backend_api.model.*;
import com.comercio_backend_api.repository.ClienteRepository;
import com.comercio_backend_api.repository.ProductoRepository;
import com.comercio_backend_api.repository.UsuarioRepository;
import com.comercio_backend_api.repository.VentaRepository;
import com.comercio_backend_api.service.VentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            ProductoRepository productoRepository,
                            UsuarioRepository usuarioRepository,
                            ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public VentaResponseDTO registrarVenta(VentaRequestDTO dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new RuntimeException("La venta debe contener al menos un producto.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario está inactivo y no puede registrar ventas.");
        }

        Cliente cliente = null;
        if (dto.getClienteId() != null) {
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
        }

        Venta venta = new Venta();
        venta.setFechaHora(LocalDateTime.now());
        venta.setUsuario(usuario);
        venta.setCliente(cliente);
        venta.setFormaPago(dto.getFormaPago());

        BigDecimal totalVenta = BigDecimal.ZERO;
        List<DetalleVenta> detallesEntidad = new ArrayList<>();

        for (DetalleVentaRequestDTO item : dto.getDetalles()) {
            if (item.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a 0.");
            }

            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getProductoId()));

            if (!producto.isEstado()) {
                throw new RuntimeException("El producto '" + producto.getNombre() + "' está dado de baja.");
            }

            // Descuenta directo: si estaba en 0 y vende 3, queda en -3 sin frenar la caja
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // Calcular precio histórico y subtotal del renglón
            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio_unitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            detallesEntidad.add(detalle);
            totalVenta = totalVenta.add(subtotal);
        }

        venta.setDetalles(detallesEntidad);
        venta.setTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.save(venta);
        return mapearAResponseDTO(ventaGuardada);
    }

    @Override
    public List<VentaResponseDTO> listarTodas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaResponseDTO obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return mapearAResponseDTO(venta);
    }

    @Override
    public List<VentaResponseDTO> listarPorFecha(LocalDate desde, LocalDate hasta) {
        LocalDateTime inicioDia = desde.atStartOfDay();
        LocalDateTime finDia = hasta.atTime(LocalTime.MAX);
        return ventaRepository.findByFechaHoraBetween(inicioDia, finDia)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaResponseDTO> listarPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    private VentaResponseDTO mapearAResponseDTO(Venta v) {
        List<DetalleVentaResponseDTO> detallesDTO = v.getDetalles().stream()
                .map(d -> new DetalleVentaResponseDTO(
                        d.getId(),
                        d.getProducto().getId(),
                        d.getProducto().getNombre(),
                        d.getProducto().getCodigoBarra(),
                        d.getCantidad(),
                        d.getPrecio_unitario(),
                        d.getSubtotal()
                ))
                .collect(Collectors.toList());

        Long clienteId = v.getCliente() != null ? v.getCliente().getId() : null;
        String clienteNombre = v.getCliente() != null
                ? v.getCliente().getNombre() + " " + v.getCliente().getApellido()
                : "Consumidor Final";

        return new VentaResponseDTO(
                v.getId(),
                v.getFechaHora(),
                v.getUsuario().getId(),
                v.getUsuario().getUsuario(),
                clienteId,
                clienteNombre,
                v.getFormaPago(),
                v.getTotal(),
                detallesDTO
        );
    }
}