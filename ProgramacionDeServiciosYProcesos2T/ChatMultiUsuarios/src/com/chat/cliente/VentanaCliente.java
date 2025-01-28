package com.chat.cliente;

import com.chat.utilities.EstiloUI;
import com.chat.utilities.Constantes;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class VentanaCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajes;
    private JTextField campoMensaje;
    private JButton botonEnviar;
    private JComboBox<String> comboDestinatarios;
    private String nombreUsuario;
    private final PrintWriter escritor;
    private final Map<String, VentanaChatPrivadoCliente> ventanasPrivadas;

    public VentanaCliente(String nombreUsuario, PrintWriter escritor) {
        this.nombreUsuario = nombreUsuario != null ? nombreUsuario : "Usuario no identificado";
        this.escritor = escritor;
        this.ventanasPrivadas = new HashMap<>();
        inicializarVentana();
    }

    private void inicializarVentana() {
        // Configurar la ventana principal
        setTitle(Constantes.TITULO_CLIENTE + ": " + nombreUsuario);
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Aplicar estilo general
        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_CLIENTE);

        // Crear el área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajes.setForeground(Color.DARK_GRAY);
        areaMensajes.setBackground(Color.WHITE);
        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para escribir mensajes
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBackground(EstiloUI.COLOR_FONDO_CLIENTE);

        campoMensaje = new JTextField();
        campoMensaje.setFont(EstiloUI.FUENTE_GENERAL);
        campoMensaje.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        botonEnviar = new JButton(Constantes.BOTON_ENVIAR);
        EstiloUI.aplicarEstiloBoton(botonEnviar);

        comboDestinatarios = new JComboBox<>();
        comboDestinatarios.addItem(Constantes.TODOS);

        panelInferior.add(comboDestinatarios, BorderLayout.WEST);
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        // Acción del botón enviar
        botonEnviar.addActionListener(e -> enviarMensaje());

        setVisible(true);
    }

    // Mostrar mensajes en el área de texto general
    public void mostrarMensajeGeneral(String mensaje) {
        SwingUtilities.invokeLater(() -> areaMensajes.append(mensaje + "\n"));
    }

    // Actualizar la lista de destinatarios
    public void actualizarDestinatarios(String usuarios) {
        SwingUtilities.invokeLater(() -> {
            comboDestinatarios.removeAllItems();
            comboDestinatarios.addItem(Constantes.TODOS);
            for (String usuario : usuarios.split(",")) {
                if (!usuario.equals(nombreUsuario)) {
                    comboDestinatarios.addItem(usuario);
                }
            }
        });
    }

    // Enviar mensajes
    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            String destinatario = (String) comboDestinatarios.getSelectedItem();
            if (Constantes.TODOS.equals(destinatario)) {
                escritor.println(Constantes.PREFIJO_GLOBAL + mensaje);
                mostrarMensajeGeneral("Tú (Global): " + mensaje);
            } else if (destinatario != null) {
                escritor.println("@" + destinatario + ":" + mensaje);
                abrirChatPrivado(destinatario).mostrarMensaje("Tú: " + mensaje);
            }
            campoMensaje.setText("");
        }
    }

    // Abrir o reutilizar ventana de chat privado
    private VentanaChatPrivadoCliente abrirChatPrivado(String destinatario) {
        return ventanasPrivadas.computeIfAbsent(destinatario, d -> {
            VentanaChatPrivadoCliente ventanaPrivada = new VentanaChatPrivadoCliente(nombreUsuario, destinatario, escritor);
            EstiloUI.aplicarEstiloVentana(ventanaPrivada, EstiloUI.COLOR_FONDO_CHAT_PRIVADO); // Estilo único para ventanas privadas
            ventanaPrivada.setVisible(true);
            return ventanaPrivada;
        });
    }

    // Mostrar mensaje en una ventana privada
    public void mostrarMensajePrivado(String remitente, String mensaje) {
        VentanaChatPrivadoCliente ventanaPrivada = abrirChatPrivado(remitente);
        ventanaPrivada.mostrarMensaje(remitente + ": " + mensaje);
    }
}
