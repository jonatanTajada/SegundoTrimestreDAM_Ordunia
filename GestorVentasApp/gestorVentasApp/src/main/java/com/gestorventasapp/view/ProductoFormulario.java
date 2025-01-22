package com.gestorventasapp.view;

import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.Producto;

import javax.swing.*;
import java.awt.*;

public class ProductoFormulario extends JDialog {

	private JTextField campoNombre;
	private JTextField campoPrecio;
	private JTextField campoStock;
	private JButton btnGuardar;
	private JButton btnCancelar;

	private ProductoController productoController;
	private Runnable onProductoGuardado;

	public ProductoFormulario(Frame parent, String titulo, ProductoController productoController,
			Runnable onProductoGuardado) {
		super(parent, titulo, true);
		this.productoController = productoController;
		this.onProductoGuardado = onProductoGuardado;

		setSize(400, 250);
		setResizable(false);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		add(crearPanelCentral(), BorderLayout.CENTER);
		add(crearPanelInferior(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblNombre = new JLabel("Nombre:");
		campoNombre = new JTextField();

		JLabel lblPrecio = new JLabel("Precio:");
		campoPrecio = new JTextField();

		JLabel lblStock = new JLabel("Stock:");
		campoStock = new JTextField();

		panelCentral.add(lblNombre);
		panelCentral.add(campoNombre);
		panelCentral.add(lblPrecio);
		panelCentral.add(campoPrecio);
		panelCentral.add(lblStock);
		panelCentral.add(campoStock);

		return panelCentral;
	}

	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new FlowLayout());

		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");

		btnGuardar.addActionListener(e -> guardarProducto());
		btnCancelar.addActionListener(e -> dispose());

		panelInferior.add(btnGuardar);
		panelInferior.add(btnCancelar);

		return panelInferior;
	}

	private void guardarProducto() {
		try {
			String nombre = campoNombre.getText().trim();
			double precio = Double.parseDouble(campoPrecio.getText().trim());
			int stock = Integer.parseInt(campoStock.getText().trim());

			Producto producto = new Producto();
			producto.setNombre(nombre);
			producto.setPrecio(precio);
			producto.setStock(stock);

			String mensaje = productoController.addProduct(nombre, precio, stock);
			JOptionPane.showMessageDialog(this, mensaje);

			if (onProductoGuardado != null) {
				onProductoGuardado.run();
			}

			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + e.getMessage());
		}
	}
}
