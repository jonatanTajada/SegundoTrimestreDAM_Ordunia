package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.controller.VentaController;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.util.List;

public class VentaView extends ModuloBaseView {

	private VentaController ventaController;
	private ClienteController clienteController;

	public VentaView() {
		super("Gestión de Ventas", new String[] { "ID Venta", "Cliente", "Fecha", "Total" });
		ventaController = new VentaController();
		clienteController = new ClienteController();

		// Agregar la barra de menú
		ventana.setJMenuBar(EstiloUI.crearBarraMenu(ventana));

		// Configurar SwingWorker para cargar datos en segundo plano
		ejecutarSwingWorker(() -> cargarDatosTabla(), // Tarea en segundo plano
				null // No se requiere tarea adicional después de cargar
		);
	}

	@Override
	protected void inicializarBotones() {
		JButton btnAgregar = new JButton("Agregar Venta");
		JButton btnEliminar = new JButton("Eliminar Venta");
		JButton btnActualizar = new JButton("Actualizar Tabla");
		JButton btnVerDetalles = new JButton("Ver Detalles");

		// Aplicar estilos a los botones
		EstiloUI.aplicarEstiloBoton(btnAgregar);
		EstiloUI.aplicarEstiloBoton(btnEliminar);
		EstiloUI.aplicarEstiloBoton(btnActualizar);
		EstiloUI.aplicarEstiloBoton(btnVerDetalles);

		// Agregar acciones
		btnAgregar.addActionListener(e -> accionAgregarVenta());
		btnEliminar.addActionListener(e -> accionEliminarVenta());
		btnActualizar.addActionListener(e -> cargarDatosTabla());
		btnVerDetalles.addActionListener(e -> abrirDetalleVenta());

		// Agregar los botones al panel
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
		panelBotones.add(btnVerDetalles);
	}

	private void cargarDatosTabla() {
		List<Venta> ventas = ventaController.getAllVentas();
		Object[][] datos = ventas.stream()
				.map(v -> new Object[] { v.getIdVenta(), v.getCliente().getNombre(), v.getFecha(), calcularTotalVenta(v) // Formatear
																															// total
																															// con
																															// dos
																															// decimales
				}).toArray(Object[][]::new);
		actualizarTabla(datos);
	}

	private void accionAgregarVenta() {
		VentaFormulario formulario = new VentaFormulario(ventana, "Agregar Venta", ventaController, clienteController,
				this::cargarDatosTabla // Callback para actualizar la tabla
		);
		formulario.setVisible(true);
	}

	private void accionEliminarVenta() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione una venta para eliminar.");
			return;
		}

		int idVenta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		String mensaje = ventaController.deleteVenta(idVenta);
		JOptionPane.showMessageDialog(ventana, mensaje);
		cargarDatosTabla();
	}

	private String calcularTotalVenta(Venta venta) {
		double total = venta.getDetallesVenta().stream()
				.mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario()).sum();
		return String.format("%.2f", total);
	}

	private void abrirDetalleVenta() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione una venta para ver los detalles.");
			return;
		}

		int idVenta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		Venta ventaSeleccionada = ventaController.getVentaById(idVenta);

		DetalleVentaView detalleVentaView = new DetalleVentaView();
		detalleVentaView.setVentaSeleccionada(ventaSeleccionada);
		detalleVentaView.mostrar();
	}

	@Override
	protected void cargarDatosOriginales() {
		cargarDatosTabla();
	}

	@Override
	protected Object[][] obtenerDatosFiltrados(String textoFiltro) {
		List<Venta> ventasFiltradas = ventaController.getAllVentas().stream()
				.filter(v -> v.getCliente().getNombre().toLowerCase().contains(textoFiltro)
						|| String.valueOf(v.getIdVenta()).contains(textoFiltro))
				.toList();

		return ventasFiltradas.stream().map(
				v -> new Object[] { v.getIdVenta(), v.getCliente().getNombre(), v.getFecha(), calcularTotalVenta(v) })
				.toArray(Object[][]::new);
	}
}
