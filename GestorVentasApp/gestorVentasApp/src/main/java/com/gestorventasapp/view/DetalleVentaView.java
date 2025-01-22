package com.gestorventasapp.view;

import com.gestorventasapp.controller.DetalleVentaController;
import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.model.Venta;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.List;

public class DetalleVentaView extends ModuloBaseView {

    private DetalleVentaController detalleVentaController;
    private ProductoController productoController; // Controlador de productos
    private Venta ventaSeleccionada; // Venta asociada


    public DetalleVentaView() {
        super("Gestión de Detalles de Ventas", new String[] { "ID Detalle", "Producto", "Cantidad", "Subtotal" });
        detalleVentaController = new DetalleVentaController();

        // Crear indicador de carga
        JLabel lblCargando = new JLabel("Cargando datos...");
        lblCargando.setHorizontalAlignment(SwingConstants.CENTER);
        ventana.add(lblCargando, BorderLayout.NORTH); // Agregar al norte de la ventana

        // Ejecutar SwingWorker para cargar datos
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                cargarDatosTabla(); // Cargar datos en segundo plano
                return null;
            }

            @Override
            protected void done() {
                ventana.remove(lblCargando); // Eliminar el indicador de carga
                ventana.revalidate(); // Actualizar la ventana
                ventana.repaint(); // Refrescar la interfaz gráfica
            }
        }.execute();
    }



    
    public void setVentaSeleccionada(Venta venta) {
        this.ventaSeleccionada = venta;
    }

    


    @Override
    protected void inicializarBotones() {
        JButton btnAgregar = new JButton("Agregar Detalle");
        JButton btnEliminar = new JButton("Eliminar Detalle");
        JButton btnActualizar = new JButton("Actualizar Tabla");

        btnAgregar.addActionListener(e -> accionAgregarDetalle());
        btnEliminar.addActionListener(e -> accionEliminarDetalle());
        btnActualizar.addActionListener(e -> cargarDatosTabla());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
    }

    private void cargarDatosTabla() {
        // Cargar detalles de ventas desde el controlador
        List<DetalleVenta> detalles = detalleVentaController.getAllDetalleVentas();
        Object[][] datos = detalles.stream()
                .map(d -> new Object[]{
                        d.getIdDetalle(),
                        d.getProducto().getNombre(),
                        d.getCantidad(),
                        d.getProducto().getPrecio(),
                        d.getSubtotal()
                })
                .toArray(Object[][]::new);
        actualizarTabla(datos);
    }

    private void accionAgregarDetalle() {
        DetalleVentaFormulario formulario = new DetalleVentaFormulario(
            ventana, // JFrame principal
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
}
