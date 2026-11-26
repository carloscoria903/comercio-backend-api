package com.comercio_backend_api.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String codigoBarra;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    private String marca;

    @Column(nullable = false)
    private BigDecimal precio;

    private int stock;

    @Column(nullable = false)
    private boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto(Long id, String codigoBarra, String nombre, String marca, String descripcion,
                    BigDecimal precio, int stock, boolean estado, Categoria categoria) {
        this.id = id;
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.categoria = categoria;
    }

    public Producto() {
    }
}
