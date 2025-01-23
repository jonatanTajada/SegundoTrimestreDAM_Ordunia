package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteView extends ModuloBaseView {

    private ClienteController clienteController;
    private List<Cliente> listaClientesOriginales; // Lista original para restaurar datos sin filtrar

    public ClienteView() {
        super("Gestión de Clientes", new String[]{"ID", "Nombre", "Email", "Teléfono"});
        clienteController = new ClienteController();

        // Agregar la barra de menú
        ventana.setJMenuBar(EstiloUI.crearBarraMenu(ventana));

        // Mostrar indicador de carga
        JLabel lblCargando = new JLabel("Cargando datos...");
        lblCargando.setHorizontalAlignment(SwingConstants.CENTER);
        ventana.add(lblCargando, BorderLayout.CENTER);

        // Cargar datos en segundo plano con SwingWorker
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                cargarDatosOriginales(); // Cargar los datos originales
                return null;
            }

            @Override
            protected void done() {
                ventana.remove(lblCargando); // Quitar el indicador de carga
                ventana.revalidate(); // Actualizar la ventana
                ventana.repaint(); // Refrescar la interfaz gráfica
            }
        }.execute();
    }

    @Override
    protected void inicializarBotones() {
        JButton btnAgregar = new JButton("Agregar Cliente");
        JButton btnEliminar = new JButton("Eliminar Cliente");
        JButton btnActualizar = new JButton("Actualizar Tabla");

        // Aplicar estilos a los botones
        EstiloUI.aplicarEstiloBoton(btnAgregar);
        EstiloUI.aplicarEstiloBoton(btnEliminar);
        EstiloUI.aplicarEstiloBoton(btnActualizar);

        // Agregar acciones
        btnAgregar.addActionListener(e -> accionAgregarCliente());
        btnEliminar.addActionListener(e -> accionEliminarCliente());
        btnActualizar.addActionListener(e -> cargarDatosOriginales());

        // Agregar los botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
    }

    @Override
    protected void cargarDatosOriginales() {
        // Obtener la lista de clientes y actualizar la tabla
        listaClientesOriginales = clienteController.getAllClientes();
        Object[][] datos = listaClientesOriginales.stream()
                .map(c -> new Object[]{c.getIdCliente(), c.getNombre(), c.getEmail(), c.getTelefono()})
                .toArray(Object[][]::new);
        actualizarTabla(datos);
    }

    @Override
    protected Object[][] obtenerDatosFiltrados(String textoFiltro) {
        // Filtrar la lista de clientes basándose en el texto ingresado
        List<Cliente> clientesFiltrados = listaClientesOriginales.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(textoFiltro)
                        || c.getEmail().toLowerCase().contains(textoFiltro)
                        || c.getTelefono().toLowerCase().contains(textoFiltro))
                .collect(Collectors.toList());

        // Convertir la lista filtrada en una matriz de datos para la tabla
        return clientesFiltrados.stream()
                .map(c -> new Object[]{c.getIdCliente(), c.getNombre(), c.getEmail(), c.getTelefono()})
                .toArray(Object[][]::new);
    }

    private void accionAgregarCliente() {
        ClienteFormularioAgregar formulario = new ClienteFormularioAgregar(ventana, "Agregar Cliente",
                clienteController, this::cargarDatosOriginales // Callback para actualizar la tabla
        );
        formulario.setVisible(true);
    }

    private void accionEliminarCliente() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(ventana, "Seleccione un cliente para eliminar.");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String mensaje = clienteController.deleteCliente(idCliente);
        JOptionPane.showMessageDialog(ventana, mensaje);
        cargarDatosOriginales();
    }
}
