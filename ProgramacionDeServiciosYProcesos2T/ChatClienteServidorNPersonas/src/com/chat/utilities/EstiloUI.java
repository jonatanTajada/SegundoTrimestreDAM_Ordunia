// Clase EstiloUI para definir y aplicar estilos personalizados en la interfaz grafica
package com.chat.utilities;

import javax.swing.*;
import java.awt.*;

public class EstiloUI {

    // Colores y fuentes personalizados para la aplicacion
    public static final Color COLOR_FONDO_SERVIDOR = new Color(220, 240, 255); 
    public static final Color COLOR_FONDO_CLIENTE = new Color(240, 255, 240); 
    public static final Color COLOR_BOTON = new Color(70, 130, 180); 
    public static final Font FUENTE_GENERAL = new Font("Arial", Font.PLAIN, 14); 
    public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 16); 

    // Aplica un color de fondo a la ventana especificada
    public static void aplicarEstiloVentana(JFrame ventana, Color colorFondo) {
        ventana.getContentPane().setBackground(colorFondo);
    }

    // Crea un JScrollPane estilizado para el JTextArea proporcionado
    public static JScrollPane crearScrollPane(JTextArea areaTexto) {
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return scrollPane;
    }

    // Aplica estilo personalizado a un boton
    public static void aplicarEstiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(FUENTE_GENERAL);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }
}
