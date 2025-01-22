package com.gestorventasapp.model;

import jakarta.persistence.*; // Anotaciones de Hibernate (JPA)
import lombok.*; // Anotaciones de Lombok

@Entity
@Table(name = "Producto") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Producto {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_producto") // Mapeo con la columna
	private int idProducto;

	@Column(name = "nombre", nullable = false, length = 50) // Restricciones de la columna
	private String nombre;

	@Column(name = "precio", nullable = false) // Precio no puede ser nulo
	private double precio;

	@Column(name = "stock", nullable = false) // Stock no puede ser nulo
	private int stock;
}
