package com.chat.utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EstiloUI {

    // Fuentes generales
    public static final Font FUENTE_GENERAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_CHAT = new Font("Consolas", Font.PLAIN, 14);

    // Colores generales
    public static final Color COLOR_TEXTO = Color.BLACK; // Color de texto general
    public static final Color COLOR_FONDO_AREA_TEXTO = Color.WHITE; // Fondo de áreas de texto

    // Estilo para ventanas de clientes
    public static final Color COLOR_FONDO_CLIENTE = new Color(245, 245, 245); // Blanco suave
    public static final Color COLOR_TITULO_CLIENTE = new Color(70, 130, 180); // Azul suave

    // Estilo para ventanas de chat privado (cliente)
    public static final Color COLOR_FONDO_CHAT_PRIVADO = new Color(255, 248, 240); // Beige claro
    public static final Color COLOR_TITULO_PRIVADO = new Color(34, 139, 34); // Verde oscuro

    // Estilo para ventanas de chat privado (servidor)
    public static final Color COLOR_FONDO_CHAT_PRIVADO_SERVIDOR = new Color(240, 255, 255); // Azul pálido

    // Estilo para la ventana del servidor
    public static final Color COLOR_FONDO_SERVIDOR = new Color(235, 240, 250); // Azul claro
    public static final Color COLOR_TITULO_SERVIDOR = new Color(60, 60, 60); // Gris oscuro

    // Color para los títulos generales en ventanas
    public static final Color COLOR_TITULO = new Color(60, 60, 60); // Gris oscuro

    // Botones
    public static final Color COLOR_BOTON = new Color(100, 149, 237); // Azul intermedio
    public static final Color COLOR_BOTON_TEXTO = Color.WHITE;

    // Métodos para aplicar estilos
    public static void aplicarEstiloVentana(JFrame ventana, Color fondo) {
        ventana.getContentPane().setBackground(fondo);
    }

    public static JLabel crearTitulo(String texto, Color color) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(color);
        titulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        return titulo;
    }

    public static JTextArea crearAreaTexto() {
        JTextArea areaTexto = new JTextArea();
        areaTexto.setFont(FUENTE_CHAT);
        areaTexto.setForeground(COLOR_TEXTO);
        areaTexto.setBackground(COLOR_FONDO_AREA_TEXTO);
        areaTexto.setEditable(false);
        areaTexto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return areaTexto;
    }

    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_GENERAL);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return boton;
    }

    public static void aplicarEstiloBoton(JButton boton) {
        boton.setFont(FUENTE_GENERAL);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    public static JScrollPane crearScrollPane(JTextArea areaTexto) {
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return scrollPane;
    }

    public static JTextField crearCampoTexto() {
        JTextField campoTexto = new JTextField();
        campoTexto.setFont(FUENTE_GENERAL);
        campoTexto.setForeground(COLOR_TEXTO);
        campoTexto.setBackground(COLOR_FONDO_AREA_TEXTO);
        campoTexto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return campoTexto;
    }
}
