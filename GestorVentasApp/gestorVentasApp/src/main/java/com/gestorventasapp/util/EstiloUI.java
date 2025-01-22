package com.gestorventasapp.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EstiloUI {

	// Estilo para los botones
	public static void aplicarEstiloBoton(JButton boton) {
	    boton.setFont(new Font("Arial", Font.BOLD, 12)); // Fuente y tamaño
	    boton.setBackground(Color.BLUE); // Fondo azul
	    boton.setForeground(Color.WHITE); // Texto blanco
	    boton.setFocusPainted(false); // Quitar borde al hacer clic
	    boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano
	    boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margen interno
	}


	// Estilo para los formularios (JLabel, JTextField)
	public static void aplicarEstiloFormulario(JComponent componente) {
		if (componente instanceof JLabel) {
			componente.setFont(new Font("Arial", Font.BOLD, 14));
		} else if (componente instanceof JTextField) {
			componente.setFont(new Font("Arial", Font.PLAIN, 14));
			componente.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		}
	}

	// Estilo para los títulos
	public static void aplicarEstiloTitulo(JLabel titulo) {
		titulo.setFont(new Font("Arial", Font.BOLD, 18));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(new Color(25, 25, 112)); // Azul oscuro
	}

	// Configurar estilo para las tablas
	public static void configurarEstiloTabla(JTable tabla) {
	    tabla.setFillsViewportHeight(true);
	    tabla.getTableHeader().setBackground(Color.BLUE); // Fondo azul para encabezados
	    tabla.getTableHeader().setForeground(Color.WHITE); // Texto blanco para encabezados
	    tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Fuente del encabezado
	    tabla.setFont(new Font("Arial", Font.PLAIN, 12)); // Fuente de las celdas
	    tabla.setRowHeight(25); // Altura de las filas
	    tabla.setGridColor(Color.LIGHT_GRAY); // Color de las líneas
	    tabla.setSelectionBackground(Color.LIGHT_GRAY); // Fondo para selección
	    tabla.setSelectionForeground(Color.BLACK); // Texto para selección
	}

}
