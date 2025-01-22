package com.gestorventasapp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Hibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_venta")
    private int idVenta;

    @Column(name = "fecha", nullable = false, updatable = false, insertable = false)
    private LocalDateTime fecha;


    @ManyToOne // Relación muchos a uno con Cliente
    @JoinColumn(name = "id_cliente", nullable = false) // Clave fora+nea en la tabla 'venta'
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) 
    private List<DetalleVenta> detallesVenta;
 
    
    // Metodos adicionales para gestionar la relacion con detallesVenta
    public void agregarDetalle(DetalleVenta detalle) {
        this.detallesVenta.add(detalle);
        detalle.setVenta(this); // Establecer la relacion bidireccional
    }

    
    public void eliminarDetalle(DetalleVenta detalle) {
        this.detallesVenta.remove(detalle);
        detalle.setVenta(null); // Romper la relacion bidireccional
    }
}
