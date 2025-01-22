package com.gestorventasapp.util;

import com.gestorventasapp.model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Renderizador personalizado para mostrar informacion detallada en JComboBox
public class ClienteCellRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value instanceof Cliente) {
			Cliente cliente = (Cliente) value;

			// Mostrar solo el nombre en el JComboBox
			label.setText(cliente.getNombre());

			// Agregar tooltip con informacion completa
			label.setToolTipText("<html>Nombre: " + cliente.getNombre() + "<br>Email: " + cliente.getEmail()
					+ "<br>Telefono: " + cliente.getTelefono() + "</html>");
		}

		// Estilo del componente
		label.setBorder(new EmptyBorder(2, 5, 2, 5)); // Margen interno
		label.setFont(new Font("Arial", Font.PLAIN, 12)); // Cambiar fuente

		return label;
	}
}
