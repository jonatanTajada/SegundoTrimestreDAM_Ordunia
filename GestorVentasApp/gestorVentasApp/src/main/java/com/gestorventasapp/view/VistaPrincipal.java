package com.gestorventasapp.view;

import javax.swing.*;
import com.gestorventasapp.util.EstiloUI;
import java.awt.*;
import java.net.URL;

public class VistaPrincipal {

	private JFrame ventana;

	public VistaPrincipal() {
		// Configuración de la ventana principal
		ventana = new JFrame("Gestión de Negocio");
		ventana.setSize(800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Crear encabezado profesional
		JPanel encabezado = crearEncabezado();
		ventana.add(encabezado, BorderLayout.NORTH);

		// Crear barra de menú
		ventana.setJMenuBar(EstiloUI.crearBarraMenu(ventana));

		// Crear panel central con botones de módulos
		JPanel panelCentral = crearPanelCentral();
		ventana.add(panelCentral, BorderLayout.CENTER);

		// Crear pie de página con botón Salir
		JPanel piePagina = crearPiePagina();
		ventana.add(piePagina, BorderLayout.SOUTH);

		// Configuración final
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	private JPanel crearEncabezado() {
		JPanel panelEncabezado = new JPanel(new BorderLayout());
		panelEncabezado.setBackground(new Color(70, 130, 180)); // Azul suave
		panelEncabezado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Título centrado
		JLabel titulo = new JLabel("Gestión de Negocio", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setForeground(Color.WHITE);

		// Logo a la derecha
		URL logoURL = getClass().getResource("/logo.jpg");
		JLabel lblLogo = new JLabel();
		if (logoURL != null) {
			ImageIcon logo = new ImageIcon(
					new ImageIcon(logoURL).getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH));
			lblLogo.setIcon(logo);
		}

		panelEncabezado.add(titulo, BorderLayout.CENTER);
		panelEncabezado.add(lblLogo, BorderLayout.EAST);

		return panelEncabezado;
	}

	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel(new GridLayout(2, 2, 20, 20));
		panelCentral.setBackground(new Color(245, 245, 245)); // Gris claro
		panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Crear botones
		JButton btnClientes = new JButton("Gestión de Clientes");
		JButton btnVentas = new JButton("Gestión de Ventas");
		JButton btnProductos = new JButton("Gestión de Productos");
		JButton btnDetalles = new JButton("Gestión de Detalles de Ventas");

		// Aplicar el nuevo estilo a los botones
		EstiloUI.aplicarEstiloBotonWorkspace(btnClientes);
		EstiloUI.aplicarEstiloBotonWorkspace(btnVentas);
		EstiloUI.aplicarEstiloBotonWorkspace(btnProductos);
		EstiloUI.aplicarEstiloBotonWorkspace(btnDetalles);

		// Agregar acciones a los botones
		btnClientes.addActionListener(e -> abrirClientes());
		btnVentas.addActionListener(e -> abrirVentas());
		btnProductos.addActionListener(e -> abrirProductos());
		btnDetalles.addActionListener(e -> abrirDetalles());

		// Agregar botones al panel
		panelCentral.add(btnClientes);
		panelCentral.add(btnVentas);
		panelCentral.add(btnProductos);
		panelCentral.add(btnDetalles);

		return panelCentral;
	}

	private JPanel crearPiePagina() {
		JPanel piePagina = new JPanel(new FlowLayout(FlowLayout.CENTER));
		piePagina.setBackground(new Color(245, 245, 245)); // Gris claro
		piePagina.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton btnSalir = new JButton("Salir");
		EstiloUI.aplicarEstiloBoton(btnSalir); // Aplicar estilo unificado
		btnSalir.addActionListener(e -> System.exit(0));

		piePagina.add(btnSalir);
		return piePagina;
	}

	// Métodos para abrir los módulos
	private void abrirClientes() {
		new ClienteView();
	}

	private void abrirVentas() {
		new VentaView();
	}

	private void abrirProductos() {
		new ProductoView();
	}

	private void abrirDetalles() {
		new DetalleVentaView();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(VistaPrincipal::new);
	}
}
