package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.awt.*;

public class ClienteFormularioAgregar extends JDialog {

	private JTextField campoNombre;
	private JTextField campoEmail;
	private JTextField campoTelefono;
	private JButton btnGuardar;
	private JButton btnCancelar;

	private ClienteController clienteController;
	private Runnable onClienteGuardado; // Callback para actualizar datos en la vista principal

	// Constructor
	public ClienteFormularioAgregar(Window parent, String titulo, ClienteController clienteController,
			Runnable onClienteGuardado) {
		super(parent, titulo, ModalityType.APPLICATION_MODAL);
		this.clienteController = clienteController;
		this.onClienteGuardado = onClienteGuardado;

		// Configuración del formulario
		setSize(400, 250);
		setResizable(false);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		// Añadir paneles al formulario
		add(crearPanelCentral(), BorderLayout.CENTER);
		add(crearPanelInferior(), BorderLayout.SOUTH);
	}

	// Crear el panel central con el formulario
	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblNombre = new JLabel("Nombre:");
		EstiloUI.aplicarEstiloFormulario(lblNombre);
		campoNombre = new JTextField();

		JLabel lblEmail = new JLabel("Email:");
		EstiloUI.aplicarEstiloFormulario(lblEmail);
		campoEmail = new JTextField();

		JLabel lblTelefono = new JLabel("Teléfono:");
		EstiloUI.aplicarEstiloFormulario(lblTelefono);
		campoTelefono = new JTextField();

		panelCentral.add(lblNombre);
		panelCentral.add(campoNombre);
		panelCentral.add(lblEmail);
		panelCentral.add(campoEmail);
		panelCentral.add(lblTelefono);
		panelCentral.add(campoTelefono);

		return panelCentral;
	}

	// Crear el panel inferior con los botones
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new FlowLayout());

		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");

		EstiloUI.aplicarEstiloBoton(btnGuardar);
		EstiloUI.aplicarEstiloBoton(btnCancelar);

		btnGuardar.addActionListener(e -> guardarCliente());
		btnCancelar.addActionListener(e -> dispose());

		panelInferior.add(btnGuardar);
		panelInferior.add(btnCancelar);

		return panelInferior;
	}

	// Método para guardar un cliente
	private void guardarCliente() {
		try {
			// Obtener valores de los campos
			String nombre = campoNombre.getText().trim();
			String email = campoEmail.getText().trim();
			String telefono = campoTelefono.getText().trim();

			// Validar campos
			if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
				throw new IllegalArgumentException("Todos los campos son obligatorios.");
			}

			Cliente nuevoCliente = new Cliente();
			nuevoCliente.setNombre(nombre);
			nuevoCliente.setEmail(email);
			nuevoCliente.setTelefono(telefono);

			// Guardar cliente
			String mensaje = clienteController.addCliente(nuevoCliente);
			JOptionPane.showMessageDialog(this, mensaje);

			// Notificar actualización si se define un callback
			if (onClienteGuardado != null) {
				onClienteGuardado.run();
			}

			dispose(); // Cerrar el formulario
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
