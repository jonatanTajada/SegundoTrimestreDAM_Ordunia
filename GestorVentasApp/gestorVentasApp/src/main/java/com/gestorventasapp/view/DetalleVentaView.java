package com.gestorventasapp.view;

import com.gestorventasapp.controller.DetalleVentaController;
import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DetalleVentaView extends ModuloBaseView {

	private DetalleVentaController detalleVentaController;
	private ProductoController productoController; // Controlador de productos
	private Venta ventaSeleccionada; // Venta asociada

	public DetalleVentaView() {
		super("Gestión de Detalles de Ventas",
				new String[] { "ID Detalle", "Producto", "Cantidad", "Precio", "Subtotal" });
		detalleVentaController = new DetalleVentaController();
		productoController = new ProductoController();

		// Configurar SwingWorker para cargar datos en segundo plano
		ejecutarSwingWorker(() -> cargarDatosTabla(), // Tarea en segundo plano
				null // No se requiere tarea adicional después de cargar
		);
	}

	public void setVentaSeleccionada(Venta venta) {
		this.ventaSeleccionada = venta;
	}

	@Override
	protected void inicializarBotones() {
		JButton btnAgregar = new JButton("Agregar Detalle");
		JButton btnEliminar = new JButton("Eliminar Detalle");
		JButton btnActualizar = new JButton("Actualizar Tabla");

		// Aplicar estilos a los botones
		EstiloUI.aplicarEstiloBoton(btnAgregar);
		EstiloUI.aplicarEstiloBoton(btnEliminar);
		EstiloUI.aplicarEstiloBoton(btnActualizar);

		// Configurar acciones de los botones
		btnAgregar.addActionListener(e -> accionAgregarDetalle());
		btnEliminar.addActionListener(e -> accionEliminarDetalle());
		btnActualizar.addActionListener(e -> cargarDatosTabla());

		// Agregar botones al panel
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
	}

	private void cargarDatosTabla() {
		// Cargar detalles de ventas desde el controlador
		List<DetalleVenta> detalles = detalleVentaController.getAllDetalleVentas();
		Object[][] datos = detalles.stream().map(d -> new Object[] { d.getIdDetalle(), d.getProducto().getNombre(),
				d.getCantidad(), d.getProducto().getPrecio(), d.getSubtotal() }).toArray(Object[][]::new);
		actualizarTabla(datos);
	}

	private void accionAgregarDetalle() {
		DetalleVentaFormulario formulario = new DetalleVentaFormulario(ventana, // JFrame principal
				"Agregar Detalle", // Título
				detalleVentaController, // Controlador de detalles
				productoController, // Controlador de productos
				ventaSeleccionada, // Venta asociada (debe estar definida)
				this::cargarDatosTabla // Callback para actualizar la tabla
		);
		formulario.setVisible(true);
	}

	private void accionEliminarDetalle() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione un detalle para eliminar.");
			return;
		}

		int idDetalle = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		String mensaje = detalleVentaController.deleteDetalleVenta(idDetalle);
		JOptionPane.showMessageDialog(ventana, mensaje);
		cargarDatosTabla();
	}

	@Override
	protected void cargarDatosOriginales() {
		cargarDatosTabla(); // Cargar los datos originales (sin filtro)
	}

	@Override
	protected Object[][] obtenerDatosFiltrados(String textoFiltro) {
		List<DetalleVenta> detalles = detalleVentaController.getAllDetalleVentas();
		return detalles.stream().filter(d -> d.getProducto().getNombre().toLowerCase().contains(textoFiltro))
				.map(d -> new Object[] { d.getIdDetalle(), d.getProducto().getNombre(), d.getCantidad(),
						d.getProducto().getPrecio(), d.getSubtotal() })
				.toArray(Object[][]::new);
	}
}
