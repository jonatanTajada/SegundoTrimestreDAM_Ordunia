package com.chat.servidor;

import com.chat.utilities.Constantes;
import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class VentanaChatPrivadoServidor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajesPrivados;
    private JTextField campoMensajePrivado;
    private JButton botonEnviarPrivado;
    private final String remitente;
    private final String destinatario;
    private final PrintWriter escritor;

    public VentanaChatPrivadoServidor(String remitente, String destinatario, PrintWriter escritor) {
        this.remitente = remitente != null ? remitente : "Remitente no identificado";
        this.destinatario = destinatario != null ? destinatario : "Destinatario no identificado";
        this.escritor = escritor;

        setTitle("Chat privado del servidor: " + remitente + " con " + destinatario);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Aplicar estilo a la ventana
        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_CHAT_PRIVADO_SERVIDOR);

        // Área de mensajes privados
        areaMensajesPrivados = new JTextArea();
        areaMensajesPrivados.setEditable(false);
        areaMensajesPrivados.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajesPrivados.setForeground(Color.BLACK);
        areaMensajesPrivados.setBackground(Color.WHITE);
        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajesPrivados);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para enviar mensajes
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBackground(EstiloUI.COLOR_FONDO_CHAT_PRIVADO_SERVIDOR);

        campoMensajePrivado = new JTextField();
        campoMensajePrivado.setFont(EstiloUI.FUENTE_GENERAL);
        campoMensajePrivado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        botonEnviarPrivado = new JButton(Constantes.BOTON_ENVIAR);
        EstiloUI.aplicarEstiloBoton(botonEnviarPrivado);

        panelInferior.add(campoMensajePrivado, BorderLayout.CENTER);
        panelInferior.add(botonEnviarPrivado, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        // Acción del botón enviar
        botonEnviarPrivado.addActionListener(e -> enviarMensajePrivado());

        setVisible(true);
    }

    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> areaMensajesPrivados.append(mensaje + "\n"));
    }

    private void enviarMensajePrivado() {
        String mensaje = campoMensajePrivado.getText().trim();
        if (!mensaje.isEmpty()) {
            // Enviar el mensaje al servidor
            escritor.println("@" + destinatario + ":" + mensaje);

            // Mostrar el mensaje en la ventana del remitente
            mostrarMensaje("Tú: " + mensaje);

            // Limpiar el campo de texto
            campoMensajePrivado.setText("");
        }
    }
}
