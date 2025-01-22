package com.gestorventasapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Detalle_Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    @jakarta.validation.constraints.Min(value = 1, message = "La cantidad debe ser mayor que 0.")
    private int cantidad;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El subtotal no puede ser negativo.")
    private BigDecimal subtotal;

    // Método para calcular el subtotal
    public void calcularSubtotal() {
        if (producto != null && cantidad > 0) {
            this.subtotal = BigDecimal.valueOf(producto.getPrecio()).multiply(BigDecimal.valueOf(cantidad));
        } else {
            this.subtotal = BigDecimal.ZERO; // Valor por defecto si no hay datos válidos
        }
    }

    // Getter para el precio unitario del producto
    public double getPrecioUnitario() {
        return producto != null ? producto.getPrecio() : 0.0;
    }
}
