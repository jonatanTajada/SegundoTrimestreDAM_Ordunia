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

    public VentanaPrivada(String usuarioDestino, ActionListener enviarAccion) {
        super("Chat privado con " + usuarioDestino);
        this.usuarioDestino = usuarioDestino;

        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        campoMensaje = new JTextField();
        botonEnviar = new JButton("Enviar");

        botonEnviar.addActionListener(enviarAccion);

        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void agregarMensaje(String mensaje) {
        areaMensajes.append(mensaje + "\n");
    }

    public String getMensaje() {
        String mensaje = campoMensaje.getText().trim();
        campoMensaje.setText(""); // Limpiar el campo después de enviar
        return mensaje;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }
}
