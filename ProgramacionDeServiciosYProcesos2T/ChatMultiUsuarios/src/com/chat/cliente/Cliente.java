package com.chat.cliente;

import com.chat.utilities.Constantes;
import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private Socket socket;
    private PrintWriter escritor;
    private BufferedReader lector;
    private JTextArea areaMensajes;
    private JTextField campoMensaje;
    private JComboBox<String> comboDestinatarios;
    private String nombreUsuario;
    private final Map<String, VentanaChatPrivadoCliente> ventanasPrivadas = new HashMap<>();

    public Cliente() {
        configurarConexion();
        inicializarVentana();
        conectarServidor();
    }

    private void inicializarVentana() {
        setTitle(Constantes.TITULO_CLIENTE + " - Usuario: " + nombreUsuario);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_CLIENTE);

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(EstiloUI.FUENTE_GENERAL);
        areaMensajes.setForeground(EstiloUI.COLOR_TEXTO);
        areaMensajes.setBackground(EstiloUI.COLOR_FONDO_AREA_TEXTO);

        JScrollPane scrollPane = EstiloUI.crearScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBackground(EstiloUI.COLOR_FONDO_CLIENTE);

        campoMensaje = EstiloUI.crearCampoTexto();

        comboDestinatarios = new JComboBox<>();
        comboDestinatarios.addItem(Constantes.TODOS);

        JButton botonEnviar = EstiloUI.crearBoton(Constantes.BOTON_ENVIAR);
        botonEnviar.addActionListener(e -> enviarMensaje());

        panelInferior.add(comboDestinatarios, BorderLayout.WEST);
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void configurarConexion() {
        JTextField campoIP = new JTextField(Constantes.SERVIDOR_IP);
        JTextField campoPuerto = new JTextField(String.valueOf(Constantes.SERVIDOR_PUERTO));
        JTextField campoNombre = new JTextField();

        Object[] campos = {
            "IP del Servidor:", campoIP,
            "Puerto de la conexión:", campoPuerto,
            "Escriba su nombre:", campoNombre
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, Constantes.TITULO_CONFIGURACION, JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            Constantes.SERVIDOR_IP = campoIP.getText().trim();
            Constantes.SERVIDOR_PUERTO = Integer.parseInt(campoPuerto.getText().trim());
            nombreUsuario = campoNombre.getText().trim();

            if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, Constantes.ERROR_NOMBRE_OBLIGATORIO);
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private void conectarServidor() {
        try {
            socket = new Socket(Constantes.SERVIDOR_IP, Constantes.SERVIDOR_PUERTO);
            escritor = new PrintWriter(socket.getOutputStream(), true);
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            escritor.println(nombreUsuario);

            new Thread(this::escucharMensajes).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, Constantes.ERROR_CONEXION_SERVIDOR + e.getMessage(), Constantes.ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void escucharMensajes() {
        try {
            String mensaje;
            while ((mensaje = lector.readLine()) != null) {
                procesarMensaje(mensaje);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, Constantes.ERROR_CONEXION_PERDIDA, Constantes.ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarMensaje(String mensaje) {
        if (mensaje.startsWith(Constantes.PREFIJO_USUARIOS_CONECTADOS)) {
            actualizarListaUsuarios(mensaje.substring(Constantes.PREFIJO_USUARIOS_CONECTADOS.length()));
        } else if (mensaje.contains(Constantes.PREFIJO_PRIVADO)) {
            procesarMensajePrivado(mensaje);
        } else {
            SwingUtilities.invokeLater(() -> {
                if (!mensaje.startsWith(nombreUsuario + "(Global):")) {
                    areaMensajes.append(mensaje + "\n");
                }
            });
        }
    }

    private void procesarMensajePrivado(String mensaje) {
        String[] partes = mensaje.split(Constantes.PREFIJO_PRIVADO);
        if (partes.length == 2) {
            String remitente = partes[0].trim();
            String contenido = partes[1].trim();

            SwingUtilities.invokeLater(() -> {
                VentanaChatPrivadoCliente ventana = abrirVentanaPrivada(remitente);
                ventana.mostrarMensaje(remitente + ": " + contenido);
            });
        }
    }

    private VentanaChatPrivadoCliente abrirVentanaPrivada(String usuario) {
        return ventanasPrivadas.computeIfAbsent(usuario, u -> {
            VentanaChatPrivadoCliente ventana = new VentanaChatPrivadoCliente(nombreUsuario, usuario, escritor);
            SwingUtilities.invokeLater(() -> ventana.setVisible(true));
            return ventana;
        });
    }

    private void actualizarListaUsuarios(String listaUsuarios) {
        SwingUtilities.invokeLater(() -> {
            comboDestinatarios.removeAllItems();
            comboDestinatarios.addItem(Constantes.TODOS);
            for (String usuario : listaUsuarios.split(",")) {
                if (!usuario.equals(nombreUsuario)) {
                    comboDestinatarios.addItem(usuario);
                }
            }
        });
    }

    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        String destinatario = (String) comboDestinatarios.getSelectedItem();

        if (!mensaje.isEmpty()) {
            if (Constantes.TODOS.equals(destinatario)) {
                escritor.println(Constantes.PREFIJO_GLOBAL + mensaje);
                mostrarMensajeGeneral("Tú (Global): " + mensaje);
            } else {
                escritor.println("@" + destinatario + ":" + mensaje);
                abrirVentanaPrivada(destinatario).mostrarMensaje("Tú: " + mensaje);
                System.out.println("Enviando mensaje privado a " + destinatario + ": " + mensaje);
            }
            campoMensaje.setText("");
        }
    }

    public void mostrarMensajeGeneral(String mensaje) {
        SwingUtilities.invokeLater(() -> areaMensajes.append(mensaje + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente::new);
    }
}
