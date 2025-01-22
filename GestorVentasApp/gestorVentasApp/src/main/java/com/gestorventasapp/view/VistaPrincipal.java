package com.gestorventasapp.view;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal {

	private JFrame ventana;

	public VistaPrincipal() {
		// Configuración de la ventana principal
		ventana = new JFrame("Gestión de Negocio");
		ventana.setSize(600, 400);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new GridLayout(2, 3, 10, 10)); // Diseño de rejilla para los botones

		// Crear botones para cada módulo
		JButton btnClientes = new JButton("Gestión de Clientes");
		JButton btnVentas = new JButton("Gestión de Ventas");
		JButton btnProductos = new JButton("Gestión de Productos");
		JButton btnDetalles = new JButton("Gestión de Detalles de Ventas"); // NUEVO BOTÓN
		JButton btnSalir = new JButton("Salir");

		// Aplicar cursor de mano para los botones
		btnClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnProductos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor para el botón nuevo
		btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Añadir acciones a los botones
		btnClientes.addActionListener(e -> abrirClientes());
		btnVentas.addActionListener(e -> abrirVentas());
		btnProductos.addActionListener(e -> abrirProductos());
		btnDetalles.addActionListener(e -> abrirDetalles()); // Acción para DetalleVentaView
		btnSalir.addActionListener(e -> System.exit(0));

		// Añadir botones a la ventana
		ventana.add(btnClientes);
		ventana.add(btnVentas);
		ventana.add(btnProductos);
		ventana.add(btnDetalles); // Agregar el nuevo botón
		ventana.add(new JLabel()); // Espacio vacío
		ventana.add(btnSalir);

		// Configuración final de la ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	// Métodos para abrir los diferentes módulos
	private void abrirClientes() {
		new ClienteView();
	}

	private void abrirVentas() {
		new VentaView();
	}

	private void abrirProductos() {
		new ProductoView();
	}

	private void abrirDetalles() { // NUEVO MÉTODO
		new DetalleVentaView();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(VistaPrincipal::new);
	}
}
