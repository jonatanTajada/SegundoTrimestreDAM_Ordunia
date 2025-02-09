package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClienteChat extends JFrame {
	
	private static final long serialVersionUID = -1499745239772132863L;
	
    private JTextArea areaChat;
    private JTextField campoMensaje;
    private JButton btnEnviar;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloUsuarios;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String nombreUsuario;
    private Socket socket;
    private Map<String, ClientePrivado> chatsPrivados = new HashMap<>();

    public ClienteChat() {
        setTitle("Chat - Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Área de chat
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        JScrollPane scrollChat = new JScrollPane(areaChat);
        panel.add(scrollChat, BorderLayout.CENTER);

        // Campo de mensaje y botón enviar
        JPanel panelInferior = new JPanel(new BorderLayout());
        campoMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");

        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(btnEnviar, BorderLayout.EAST);
        panel.add(panelInferior, BorderLayout.SOUTH);

        // Lista de usuarios conectados
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(150, 0));
        panel.add(scrollUsuarios, BorderLayout.EAST);

        add(panel);

        // Evento para enviar mensajes
        btnEnviar.addActionListener(e -> enviarMensaje());
        campoMensaje.addActionListener(e -> enviarMensaje());

        // Evento para abrir chat privado con doble clic
        listaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    String usuarioSeleccionado = listaUsuarios.getSelectedValue();
                    if (usuarioSeleccionado != null && !usuarioSeleccionado.equals(nombreUsuario)) {
                        abrirChatPrivado(usuarioSeleccionado);
                    }
                }
            }
        });

        conectarAlServidor();
    }

    private void conectarAlServidor() {
        try {
            String servidor = JOptionPane.showInputDialog(this, "Ingrese la dirección IP del servidor:", "127.0.0.1");
            String puertoStr = JOptionPane.showInputDialog(this, "Ingrese el puerto del servidor:", "5003");
            int puerto = Integer.parseInt(puertoStr);

            socket = new Socket(servidor, puerto);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre de usuario
            nombreUsuario = JOptionPane.showInputDialog(this, "Ingrese su nombre de usuario:");
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre de usuario no válido. Saliendo...", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            salida.println(nombreUsuario); // Enviar nombre al servidor

            // Hilo para recibir mensajes del servidor
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        if (mensaje.startsWith("[Usuarios]")) {
                            actualizarListaUsuarios(mensaje.substring(10));
                        } else if (mensaje.startsWith("[Privado]")) {
                            manejarMensajePrivado(mensaje);
                        } else {
                            areaChat.append(mensaje + "\n");
                        }
                    }
                } catch (IOException e) {
                    areaChat.append("Conexión cerrada.\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            salida.println(mensaje);
            campoMensaje.setText("");
        }
    }

    private void actualizarListaUsuarios(String lista) {
        SwingUtilities.invokeLater(() -> {
            modeloUsuarios.clear();
            String[] usuarios = lista.split(",");
            for (String usuario : usuarios) {
                if (!usuario.equals(nombreUsuario)) { // No incluirse a sí mismo
                    modeloUsuarios.addElement(usuario);
                }
            }
        });
    }

    private void abrirChatPrivado(String usuarioDestino) {
        ClientePrivado chatPrivado = chatsPrivados.get(usuarioDestino);
        if (chatPrivado == null) {
            chatPrivado = new ClientePrivado(usuarioDestino, salida);
            chatsPrivados.put(usuarioDestino, chatPrivado);
        }
        chatPrivado.setVisible(true);
    }

    private void manejarMensajePrivado(String mensaje) {
        // Mensaje esperado: [Privado] Usuario: mensaje
        String[] partes = mensaje.split(" ", 3);
        if (partes.length < 3) return;

        String usuarioRemitente = partes[1].replace(":", "");
        String contenido = partes[2];

        ClientePrivado chatPrivado = chatsPrivados.get(usuarioRemitente);
        if (chatPrivado == null) {
            chatPrivado = new ClientePrivado(usuarioRemitente, salida);
            chatsPrivados.put(usuarioRemitente, chatPrivado);
        }
        chatPrivado.recibirMensajePrivado(usuarioRemitente + ": " + contenido);
        chatPrivado.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteChat cliente = new ClienteChat();
            cliente.setVisible(true);
        });
    }
}
