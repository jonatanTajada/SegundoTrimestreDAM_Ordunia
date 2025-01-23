package com.gestorventasapp.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.gestorventasapp.controller.DetalleVentaController;
import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.model.Producto;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

public class DetalleVentaFormulario extends JDialog {

    private JComboBox<Producto> comboProducto; // ComboBox para seleccionar producto
    private JTextField campoCantidad; // Campo de texto para ingresar cantidad
    private JLabel lblSubtotal; // Etiqueta para mostrar el subtotal
    private JButton btnGuardar; // Botón para guardar
    private JButton btnCancelar; // Botón para cancelar

    private DetalleVentaController detalleVentaController;
    private ProductoController productoController; // Controlador para productos
    private DetalleVenta detalleVenta; // DetalleVenta existente (en caso de editar)
    private Venta venta; // Venta asociada al detalle
    private Runnable callback; // Callback para actualizar la vista principal

    // Constructor
    public DetalleVentaFormulario(JFrame parent, String titulo, DetalleVentaController detalleVentaController,
                                   ProductoController productoController, Venta venta, Runnable callback) {
        super(parent, titulo, true);
        this.detalleVentaController = detalleVentaController;
        this.productoController = productoController;
        this.venta = venta;
        this.callback = callback;

        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Crear y agregar paneles
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    // Crear el panel superior (encabezado con título)
    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel();
        JLabel lblTitulo = new JLabel("Detalle de Venta");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        EstiloUI.aplicarEstiloEtiqueta(lblTitulo);
        panelSuperior.add(lblTitulo);
        return panelSuperior;
    }

    // Crear el panel central (formulario)
    private JPanel crearPanelCentral() {
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblProducto = new JLabel("Producto:");
        EstiloUI.aplicarEstiloEtiqueta(lblProducto);

        comboProducto = new JComboBox<>();
        cargarProductos(); // Cargar productos en el combo
        comboProducto.addActionListener(e -> calcularSubtotal()); // Recalcular subtotal al seleccionar producto

        JLabel lblCantidad = new JLabel("Cantidad:");
        EstiloUI.aplicarEstiloEtiqueta(lblCantidad);

        campoCantidad = new JTextField();
        campoCantidad.addCaretListener(e -> calcularSubtotal()); // Recalcular subtotal al cambiar cantidad

        JLabel lblSubtotalText = new JLabel("Subtotal:");
        EstiloUI.aplicarEstiloEtiqueta(lblSubtotalText);

        lblSubtotal = new JLabel("0.00");
        EstiloUI.aplicarEstiloEtiqueta(lblSubtotal);

        panelCentral.add(lblProducto);
        panelCentral.add(comboProducto);
        panelCentral.add(lblCantidad);
        panelCentral.add(campoCantidad);
        panelCentral.add(lblSubtotalText);
        panelCentral.add(lblSubtotal);

        return panelCentral;
    }

    // Crear el panel inferior (botones)
    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new FlowLayout());

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        EstiloUI.aplicarEstiloBoton(btnGuardar);
        EstiloUI.aplicarEstiloBoton(btnCancelar);

        btnGuardar.addActionListener(e -> guardarDetalleVenta());
        btnCancelar.addActionListener(e -> dispose());

        panelInferior.add(btnGuardar);
        panelInferior.add(btnCancelar);

        return panelInferior;
    }

    // Cargar productos en el JComboBox con tooltips
    private void cargarProductos() {
        comboProducto.removeAllItems();
        List<Producto> productos = productoController.getAllProducts();
        for (Producto producto : productos) {
            comboProducto.addItem(producto);
        }

        // Añadir tooltips personalizados
        comboProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Producto) {
                    Producto producto = (Producto) value; // Casting explícito

                    // Texto que se muestra en el JComboBox
                    setText(producto.getNombre());

                    // Tooltip con un diseño más personalizado
                    String toolTip = "<html>"
                        + "<b>ID:</b> " + producto.getIdProducto() + "<br>"
                        + "<b>Precio:</b> " + producto.getPrecio() + "<br>"
                        + "<b>Stock:</b> " + producto.getStock() + "<br>"
                        + "</html>";
                    setToolTipText(toolTip);
                }

                return renderer;
            }
        });


    }

    // Calcular el subtotal basado en producto y cantidad
    private void calcularSubtotal() {
        try {
            Producto productoSeleccionado = (Producto) comboProducto.getSelectedItem();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());

            if (productoSeleccionado != null && cantidad > 0) {
                BigDecimal subtotal = BigDecimal.valueOf(productoSeleccionado.getPrecio())
                        .multiply(BigDecimal.valueOf(cantidad));
                lblSubtotal.setText(subtotal.toString());
            } else {
                lblSubtotal.setText("0.00");
            }
        } catch (NumberFormatException e) {
            lblSubtotal.setText("0.00"); // Subtotal por defecto si la cantidad no es válida
        }
    }

    // Guardar o actualizar el detalle de venta
    private void guardarDetalleVenta() {
        try {
            Producto productoSeleccionado = (Producto) comboProducto.getSelectedItem();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());

            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (detalleVenta == null) { // Crear nuevo detalle de venta
                String mensaje = detalleVentaController.addDetalleVenta(venta, productoSeleccionado, cantidad);
                JOptionPane.showMessageDialog(this, mensaje);
            } else { // Actualizar detalle de venta existente
                String mensaje = detalleVentaController.updateDetalleVenta(detalleVenta.getIdDetalle(), venta,
                        productoSeleccionado, cantidad);
                JOptionPane.showMessageDialog(this, mensaje);
            }

            if (callback != null) {
                callback.run(); // Ejecutar callback para actualizar la tabla en la vista principal
            }

            dispose(); // Cerrar el formulario
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el detalle de venta: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
