package com.gestorventasapp.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.gestorventasapp.util.EstiloUI;

public abstract class ModuloBaseView {
    protected JFrame ventana;
    protected JTable tabla;
    protected DefaultTableModel modeloTabla;
    protected JPanel panelBotones;

    public ModuloBaseView(String titulo, String[] columnas) {
        ventana = new JFrame(titulo);
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // Inicializar tabla
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        EstiloUI.configurarEstiloTabla(tabla); // Aplicar estilo a la tabla
        ventana.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Inicializar botones
        panelBotones = new JPanel(new FlowLayout());
        inicializarBotones();
        ventana.add(panelBotones, BorderLayout.SOUTH);

        // Ventana visible por defecto al crear un módulo
        ventana.setVisible(true);
    }

    // Método para inicializar los botones (debe ser implementado por las clases hijas)
    protected abstract void inicializarBotones();

    // Método para actualizar la tabla con nuevos datos
    protected void actualizarTabla(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Métodos para controlar la visibilidad de la ventana
    public void mostrar() {
        ventana.setVisible(true);
    }

    public void ocultar() {
        ventana.setVisible(false);
    }
}
