package com.chat.cliente;

import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class VentanaChatPrivadoCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajes;
    private JTextField campoMensaje;
    private final PrintWriter escritor;
    private final String destinatario;

    public VentanaChatPrivadoCliente(String remitente, String destinatario, PrintWriter escritor) {
        this.escritor = escritor;
        this.destinatario = destinatario;

        setTitle("Chat privado: " + remitente + " con " + destinatario);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajes.setBackground(EstiloUI.COLOR_FONDO_CHAT_PRIVADO);
        areaMensajes.setForeground(EstiloUI.COLOR_TEXTO);

        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        campoMensaje = EstiloUI.crearCampoTexto();
        JButton botonEnviar = EstiloUI.crearBoton("Enviar");

        botonEnviar.addActionListener(e -> enviarMensajePrivado());

        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> areaMensajes.append(mensaje + "\n"));
    }

    private void enviarMensajePrivado() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            escritor.println("@" + destinatario + ":" + mensaje);
            mostrarMensaje("Tú: " + mensaje);
            campoMensaje.setText("");
        }
    }
}
