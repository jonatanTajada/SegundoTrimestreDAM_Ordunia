package com.gestorventasapp.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.gestorventasapp.controller.DetalleVentaController;
import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

public class DetalleVentaView extends ModuloBaseView {

	private DetalleVentaController detalleVentaController;
	private ProductoController productoController; // Controlador de productos
	private Venta ventaSeleccionada; // Venta asociada

	public DetalleVentaView() {
		super("Gestión de Detalles de Ventas",
				new String[] { "ID Detalle", "Producto", "Cantidad", "Precio", "Subtotal" });

		detalleVentaController = new DetalleVentaController();
		productoController = new ProductoController();

		// Agregar la barra de menú
		ventana.setJMenuBar(EstiloUI.crearBarraMenu(ventana));

		// Configurar SwingWorker para cargar datos en segundo plano
		ejecutarSwingWorker(() -> cargarDatosTabla(), null);
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

		// Configurar tooltips
		EstiloUI.configurarToolTip(btnAgregar, "Añadir un nuevo detalle de venta.");
		EstiloUI.configurarToolTip(btnEliminar, "Eliminar el detalle seleccionado.");
		EstiloUI.configurarToolTip(btnActualizar, "Actualizar la tabla con los datos más recientes.");

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
		List<DetalleVenta> detalles = detalleVentaController.getAllDetalleVentas();
		Object[][] datos = detalles.stream().map(d -> new Object[] { d.getIdDetalle(), d.getProducto().getNombre(),
				d.getCantidad(), d.getProducto().getPrecio(), d.getSubtotal() }).toArray(Object[][]::new);
		actualizarTabla(datos);
	}

	private void accionAgregarDetalle() {
		DetalleVentaFormulario formulario = new DetalleVentaFormulario(ventana, "Agregar Detalle",
				detalleVentaController, productoController, ventaSeleccionada, this::cargarDatosTabla);
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
		cargarDatosTabla();
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
