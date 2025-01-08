package com.chat.utilities;

import javax.swing.*;
import java.awt.*;

public class EstiloUI {

    public static final Color COLOR_FONDO_SERVIDOR = new Color(220, 240, 255); // Azul claro suave
    public static final Color COLOR_FONDO_CLIENTE = new Color(240, 255, 240); // Verde claro suave
    public static final Color COLOR_BOTON = new Color(70, 130, 180); // Azul acero
    public static final Font FUENTE_GENERAL = new Font("Arial", Font.PLAIN, 14);
    public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 16);

    public static void aplicarEstiloVentana(JFrame ventana, Color colorFondo) {
        ventana.getContentPane().setBackground(colorFondo);
    }

    public static JScrollPane crearScrollPane(JTextArea areaTexto) {
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return scrollPane;
    }

    public static void aplicarEstiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(FUENTE_GENERAL);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }
}
