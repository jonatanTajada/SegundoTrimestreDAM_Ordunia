// Clase Cliente para gestionar la conexión y comunicación con el servidor de chat.
// Permite enviar y recibir mensajes globales o privados y gestiona las ventanas privadas.
package com.chat.cliente;

import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea areaMensajes; 
    private JTextField campoMensaje; 
    private JButton botonEnviar; 
    private JComboBox<String> comboDestinatarios; // Lista desplegable para seleccionar destinatarios
    private Socket socket; 
    private BufferedReader lector; 
    private PrintWriter escritor; 
    private String nombreUsuario; 
    private String servidorIP; 
    private int servidorPuerto; 
    private final Map<String, VentanaPrivada> ventanasPrivadas = new ConcurrentHashMap<>(); // Ventanas privadas abiertas

    // Constructor de la clase Cliente
    public Cliente() {
        super("Cliente de Chat");
        configurarConexion(); 
        inicializarVentana(); 
        conectarServidor(); 
    }

    // Solicita al usuario los datos de conexion (IP, puerto y nombre)
    private void configurarConexion() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Campos para IP, puerto y nombre
        JTextField campoIP = new JTextField("127.0.0.1");
        JTextField campoPuerto = new JTextField("10101");
        JTextField campoNombre = new JTextField();

        panel.add(new JLabel("IP del Servidor:"));
        panel.add(campoIP);
        panel.add(new JLabel("Puerto de conexión:"));
        panel.add(campoPuerto);
        panel.add(new JLabel("Escribe tu nombre:"));
        panel.add(campoNombre);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Configuraciones de la comunicación",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                servidorIP = campoIP.getText().trim();
                servidorPuerto = Integer.parseInt(campoPuerto.getText().trim());
                nombreUsuario = campoNombre.getText().trim();

                if (servidorIP.isEmpty() || nombreUsuario.isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos deben estar completos.");
                }
            } catch (NumberFormatException e) {
                mostrarError("El puerto debe ser un número válido.");
                System.exit(0);
            } catch (IllegalArgumentException e) {
                mostrarError(e.getMessage());
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    // Inicializa la ventana principal del cliente
    private void inicializarVentana() {
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Aplica el estilo personalizado a la ventana
        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_CLIENTE);

        // Area de texto para mostrar mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajes.setForeground(Color.DARK_GRAY);
        areaMensajes.setBackground(Color.WHITE);
        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para el campo de texto y boton
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBackground(EstiloUI.COLOR_FONDO_CLIENTE);

        // Campo de texto para escribir mensajes
        campoMensaje = new JTextField();
        campoMensaje.setFont(EstiloUI.FUENTE_GENERAL);
        campoMensaje.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Boton para enviar mensajes
        botonEnviar = new JButton("Enviar");
        EstiloUI.aplicarEstiloBoton(botonEnviar);

        // Lista desplegable para seleccionar destinatarios
        comboDestinatarios = new JComboBox<>();
        comboDestinatarios.addItem("Todos");

        panelInferior.add(comboDestinatarios, BorderLayout.WEST);
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);

        // Accion para el boton enviar
        botonEnviar.addActionListener(e -> enviarMensaje());
    }

    // Establece la conexion con el servidor
    private void conectarServidor() {
        try {
            socket = new Socket(servidorIP, servidorPuerto);
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            escritor = new PrintWriter(socket.getOutputStream(), true);

            // Envia el nombre del usuario al servidor
            escritor.println(nombreUsuario);

            areaMensajes.append("Conectado al servidor como " + nombreUsuario + ".\n");

            // Inicia un hilo para escuchar mensajes del servidor
            new Thread(this::escucharMensajes).start();

        } catch (IOException e) {
            mostrarError("No se pudo conectar al servidor en " + servidorIP + ":" + servidorPuerto);
            System.exit(0);
        }
    }

    // Escucha los mensajes entrantes del servidor
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

    // Procesa un mensaje recibido del servidor
    private void procesarMensaje(String mensaje) {
        if (mensaje.startsWith("Usuarios conectados:")) {

            actualizarListaUsuarios(mensaje.replace("Usuarios conectados:", "").trim());
        } else if (mensaje.contains("|")) {
            // Mensaje privado
            String[] partes = mensaje.split("\\|", 2);
            if (partes.length == 2) {
                String remitente = partes[0].trim();
                String contenido = partes[1].trim();
                abrirVentanaPrivada(remitente).agregarMensaje(remitente + ": " + contenido);
            }
        } else {
            // Mensaje general
            SwingUtilities.invokeLater(() -> areaMensajes.append(mensaje + "\n"));
        }
    }

    // Actualiza la lista de usuarios conectados en el cliente
    private void actualizarListaUsuarios(String listaUsuarios) {
        SwingUtilities.invokeLater(() -> {
            comboDestinatarios.removeAllItems();
            comboDestinatarios.addItem("Todos");
            for (String usuario : listaUsuarios.split(",")) {
                if (!usuario.equals(nombreUsuario)) {
                    comboDestinatarios.addItem(usuario);
                }
            }
        });
    }

    // Envia un mensaje al servidor
    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            String destinatario = (String) comboDestinatarios.getSelectedItem();
            if ("Todos".equals(destinatario)) {
                // Mensaje global
                escritor.println("Global:" + mensaje);
            } else {
                // Mensaje privado
                escritor.println("@" + destinatario + "|" + mensaje);
                abrirVentanaPrivada(destinatario).agregarMensaje("Yo: " + mensaje);
            }
            campoMensaje.setText("");
        }
    }

    // Abre una ventana privada para el usuario especificado o la recupera si ya existe
    private VentanaPrivada abrirVentanaPrivada(String usuario) {
        return ventanasPrivadas.computeIfAbsent(usuario, u -> new VentanaPrivada(u, e -> {
            String mensaje = ventanasPrivadas.get(u).getMensaje();
            if (!mensaje.isEmpty()) {
                escritor.println("@" + u + "|" + mensaje);
                ventanasPrivadas.get(u).agregarMensaje("Yo: " + mensaje);
            }
        }));
    }

    // Cierra la conexion con el servidor
    private void cerrarConexion() {
        try {
            if (lector != null) lector.close();
            if (escritor != null) escritor.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            mostrarError("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Muestra un mensaje de error en un cuadro de dialogo
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Metodo principal para ejecutar el cliente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente::new);
    }
}
