package com.comercio_backend_api.model;

import com.comercio_backend_api.model.enums.FormaPago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora; // registrar la fecha del cobro

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // guardar el empleado o usuario que realizo la venta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPago formaPago;

    @Column(nullable = false)
    private BigDecimal total;

    // Relación OneToMany: Una venta contiene múltiples renglones/detalles
    @OneToMany(mappedBy = "ventas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();


    public Venta(Long id, LocalDateTime fechaHora, Cliente cliente, Usuario usuario,
                 FormaPago formaPago, BigDecimal total, List<DetalleVenta> detalles) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.total = total;
        this.detalles = detalles;
    }

    public Venta() {
    }
}
