package com.chat.cliente;

import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajes;
    private JTextField campoMensaje;
    private JButton botonEnviar;
    private JComboBox<String> comboDestinatarios;
    private Socket socket;
    private BufferedReader lector;
    private PrintWriter escritor;
    private String nombreUsuario;
    private final Map<String, JFrame> ventanasPrivadas = new ConcurrentHashMap<>();

    public Cliente() {
        super("Cliente de Chat");
        inicializarVentana();
        configurarUsuario();
        conectarServidor();
    }

    private void inicializarVentana() {
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_CLIENTE);

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajes.setForeground(Color.DARK_GRAY);
        areaMensajes.setBackground(Color.WHITE);
        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBackground(EstiloUI.COLOR_FONDO_CLIENTE);

        campoMensaje = new JTextField();
        campoMensaje.setFont(EstiloUI.FUENTE_GENERAL);
        campoMensaje.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        botonEnviar = new JButton("Enviar");
        EstiloUI.aplicarEstiloBoton(botonEnviar);

        comboDestinatarios = new JComboBox<>();
        comboDestinatarios.addItem("Todos");

        panelInferior.add(comboDestinatarios, BorderLayout.WEST);
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);

        botonEnviar.addActionListener(e -> enviarMensaje());
    }

    private void configurarUsuario() {
        nombreUsuario = JOptionPane.showInputDialog(this, "Introduce tu nombre:", "Configuración", JOptionPane.QUESTION_MESSAGE);
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void conectarServidor() {
        try {
            socket = new Socket("127.0.0.1", 10101);
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            escritor = new PrintWriter(socket.getOutputStream(), true);

            escritor.println(nombreUsuario);

            areaMensajes.append("Conectado al servidor como " + nombreUsuario + ".\n");

            new Thread(this::escucharMensajes).start();

        } catch (IOException e) {
            mostrarError("No se pudo conectar al servidor: " + e.getMessage());
        }
    }

    private void escucharMensajes() {
        try {
            String mensaje;
            while ((mensaje = lector.readLine()) != null) {
                procesarMensaje(mensaje);
            }
        } catch (IOException e) {
            mostrarError("Conexión perdida con el servidor.");
        } finally {
            cerrarConexion();
        }
    }

    private void procesarMensaje(String mensaje) {
        if (mensaje.startsWith("Usuarios conectados:")) {
            actualizarListaUsuarios(mensaje.replace("Usuarios conectados:", "").trim());
        } else if (mensaje.contains("(Privado):")) {
            String[] partes = mensaje.split(":", 3);
            if (partes.length == 3) {
                String remitente = partes[0].trim();
                String contenido = partes[2].trim();
                abrirVentanaPrivada(remitente, contenido);
            }
        } else {
            SwingUtilities.invokeLater(() -> areaMensajes.append(mensaje + "\n"));
        }
    }

    private void actualizarListaUsuarios(String listaUsuarios) {
        SwingUtilities.invokeLater(() -> {
            comboDestinatarios.removeAllItems();
            comboDestinatarios.addItem("Todos");
            if (!listaUsuarios.isEmpty()) {
                ArrayList<String> usuarios = new ArrayList<>(Arrays.asList(listaUsuarios.split(",")));
                usuarios.stream()
                        .filter(usuario -> !usuario.equals(nombreUsuario))
                        .forEach(comboDestinatarios::addItem);
            }
        });
    }

    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            String destinatario = (String) comboDestinatarios.getSelectedItem();
            if ("Todos".equals(destinatario)) {
                escritor.println("Global:" + mensaje);
            } else {
                escritor.println("@" + destinatario + ":" + mensaje);
                abrirVentanaPrivada(destinatario, "Yo: " + mensaje); // Abrir ventana para el remitente
            }
            campoMensaje.setText("");
        }
    }

    private void abrirVentanaPrivada(String remitente, String mensaje) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventanaPrivada = ventanasPrivadas.computeIfAbsent(remitente, usuario -> {
                JFrame nuevaVentana = new JFrame("Chat privado con " + usuario);
                nuevaVentana.setSize(400, 400);
                nuevaVentana.setLocationRelativeTo(null);
                nuevaVentana.setLayout(new BorderLayout());

                JTextArea areaChat = new JTextArea();
                areaChat.setEditable(false);
                JScrollPane scroll = new JScrollPane(areaChat);
                nuevaVentana.add(scroll, BorderLayout.CENTER);

                JTextField campoMensajePrivado = new JTextField();
                JButton botonEnviarPrivado = new JButton("Enviar");
                botonEnviarPrivado.addActionListener(e -> {
                    String mensajePrivado = campoMensajePrivado.getText().trim();
                    if (!mensajePrivado.isEmpty()) {
                        escritor.println("@" + usuario + ":" + mensajePrivado);
                        areaChat.append("Yo: " + mensajePrivado + "\n");
                        campoMensajePrivado.setText("");
                    }
                });

                JPanel panelInferior = new JPanel(new BorderLayout());
                panelInferior.add(campoMensajePrivado, BorderLayout.CENTER);
                panelInferior.add(botonEnviarPrivado, BorderLayout.EAST);

                nuevaVentana.add(panelInferior, BorderLayout.SOUTH);
                nuevaVentana.setVisible(true);
                return nuevaVentana;
            });

            JTextArea areaChat = (JTextArea) ((JScrollPane) ventanaPrivada.getContentPane().getComponent(0)).getViewport().getView();
            areaChat.append(remitente + ": " + mensaje + "\n");
            ventanaPrivada.toFront();
        });
    }

    private void cerrarConexion() {
        try {
            if (lector != null) lector.close();
            if (escritor != null) escritor.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            mostrarError("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente::new);
    }
}