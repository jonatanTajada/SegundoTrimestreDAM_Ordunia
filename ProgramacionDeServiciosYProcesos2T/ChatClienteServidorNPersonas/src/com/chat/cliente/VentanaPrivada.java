// Clase VentanaPrivada para gestionar el chat privado entre usuarios
package com.chat.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrivada extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajes; 
    private JTextField campoMensaje; 
    private JButton botonEnviar; 
    private String usuarioDestino;

    // Constructor de la clase VentanaPrivada
    public VentanaPrivada(String usuarioDestino, ActionListener enviarAccion) {
        super("Chat privado con " + usuarioDestino);
        this.usuarioDestino = usuarioDestino;

        // Configuracion de la ventana
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Area de texto para mostrar mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para el campo de texto y boton
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        campoMensaje = new JTextField();
        botonEnviar = new JButton("Enviar");

        // Asigna la accion del boton enviar
        botonEnviar.addActionListener(enviarAccion);

        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Agrega un mensaje al area de texto
    public void agregarMensaje(String mensaje) {
        areaMensajes.append(mensaje + "\n");
    }

    // Obtiene el mensaje del campo de texto y lo limpia
    public String getMensaje() {
        String mensaje = campoMensaje.getText().trim();
        campoMensaje.setText(""); // Limpia el campo despues de enviar
        return mensaje;
    }

    // Devuelve el nombre del usuario destino
    public String getUsuarioDestino() {
        return usuarioDestino;
    }
}
