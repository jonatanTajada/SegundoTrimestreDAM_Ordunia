package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.controller.VentaController;
import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentaFormulario extends JDialog {

	private JComboBox<Cliente> comboCliente;
	private JTextField campoFecha;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnNuevoCliente;

	private VentaController ventaController;
	private ClienteController clienteController;
	private Venta venta;
	private JFrame parent;

	// Constructor del formulario
	public VentaFormulario(JFrame parent, String titulo, VentaController ventaController,
			ClienteController clienteController, Runnable callbackActualizarTabla) {
		super(parent, titulo, true);
		this.ventaController = ventaController;
		this.clienteController = clienteController;
		this.callbackActualizarTabla = callbackActualizarTabla;

		// Configuración inicial del formulario
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		// Crear y añadir paneles
		add(crearPanelSuperior(), BorderLayout.NORTH);
		add(crearPanelCentral(), BorderLayout.CENTER);
		add(crearPanelInferior(), BorderLayout.SOUTH);

		// Inicializar valores
		if (venta == null) {
			cargarClientes();
			campoFecha.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		} else {
			cargarDatosVenta();
		}
	}

//Propiedad del callback
	private final Runnable callbackActualizarTabla;

//Método para ejecutar el callback después de guardar/actualizar
	private void ejecutarCallback() {
		if (callbackActualizarTabla != null) {
			callbackActualizarTabla.run();
		}
	}

	// Crear el panel superior (Título)
	private JPanel crearPanelSuperior() {
		JPanel panelSuperior = new JPanel();
		JLabel lblTitulo = new JLabel(venta == null ? "Agregar Venta" : "Actualizar Venta");
		EstiloUI.aplicarEstiloEtiqueta(lblTitulo);
		panelSuperior.add(lblTitulo);
		return panelSuperior;
	}

	// Crear el panel central (Formulario)
	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblCliente = new JLabel("Cliente:");
		EstiloUI.aplicarEstiloEtiqueta(lblCliente);

		comboCliente = new JComboBox<>();
		comboCliente.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Cliente) {
					Cliente cliente = (Cliente) value;
					setText(cliente.getNombre() + " (" + cliente.getIdCliente() + ")");
					setToolTipText("Email: " + cliente.getEmail() + ", Teléfono: " + cliente.getTelefono());
				}
				return this;
			}
		});

		btnNuevoCliente = new JButton("Añadir Cliente");
		EstiloUI.aplicarEstiloBoton(btnNuevoCliente);
		btnNuevoCliente.addActionListener(e -> abrirFormularioCliente());

		JLabel lblFecha = new JLabel("Fecha:");
		EstiloUI.aplicarEstiloEtiqueta(lblFecha);
		campoFecha = new JTextField();

		panelCentral.add(lblCliente);
		panelCentral.add(comboCliente);
		panelCentral.add(new JLabel()); // Espacio en blanco
		panelCentral.add(btnNuevoCliente);
		panelCentral.add(lblFecha);
		panelCentral.add(campoFecha);

		return panelCentral;
	}

	// Crear el panel inferior (Botones)
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new FlowLayout());

		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");

		EstiloUI.aplicarEstiloBoton(btnGuardar);
		EstiloUI.aplicarEstiloBoton(btnCancelar);

		btnGuardar.addActionListener(e -> guardarVenta());
		btnCancelar.addActionListener(e -> dispose());

		panelInferior.add(btnGuardar);
		panelInferior.add(btnCancelar);

		return panelInferior;
	}

	// Cargar los clientes en el combo
	private void cargarClientes() {
		comboCliente.removeAllItems();
		List<Cliente> clientes = clienteController.getAllClientes();
		for (Cliente cliente : clientes) {
			comboCliente.addItem(cliente);
		}
	}

	// Cargar los datos de una venta existente
	private void cargarDatosVenta() {
		cargarClientes(); // Asegurarse de que el combo esté lleno
		comboCliente.setSelectedItem(venta.getCliente());
		campoFecha.setText(venta.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
	}

	// Guardar o actualizar la venta
	// Guardar o actualizar la venta
	private void guardarVenta() {
		try {
			Cliente clienteSeleccionado = (Cliente) comboCliente.getSelectedItem();
			if (clienteSeleccionado == null) {
				JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDateTime fecha = LocalDateTime.parse(campoFecha.getText(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

			if (venta == null) { // Crear nueva venta
				String mensaje = ventaController.addVenta(clienteSeleccionado, fecha);
				JOptionPane.showMessageDialog(this, mensaje);
			} else { // Actualizar venta existente
				venta.setCliente(clienteSeleccionado);
				venta.setFecha(fecha);
				String mensaje = ventaController.updateVenta(venta.getIdVenta(), clienteSeleccionado, fecha);
				JOptionPane.showMessageDialog(this, mensaje);
			}

			// Cerrar la ventana actual
			dispose();

			// Cerrar todas las ventanas excepto la principal
			for (Frame frame : Frame.getFrames()) {
				if (frame instanceof JFrame && frame != parent) {
					frame.dispose(); // Cierra las ventanas secundarias
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al guardar la venta: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Abrir formulario para agregar cliente
	// Abrir formulario para agregar cliente
	private void abrirFormularioCliente() {
		ClienteFormularioAgregar formularioCliente = new ClienteFormularioAgregar(this, "Agregar Cliente",
				clienteController, this::cargarClientes // Callback para recargar la lista después de agregar cliente
		);
		formularioCliente.setVisible(true);
	}

}
