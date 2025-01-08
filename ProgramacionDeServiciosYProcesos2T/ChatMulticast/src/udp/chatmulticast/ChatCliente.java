package udp.chatmulticast;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Clase ChatCliente que implementa un cliente de chat utilizando Multicast. Los
 * mensajes enviados se comparten con todos los participantes del grupo. Incluye
 * una interfaz gráfica con Swing.
 */
public class ChatCliente extends JFrame {
	
	/**
	 * Área de texto donde se mostrarán los mensajes del chat.
	 */
	private JTextArea areaMensajes;

	/**
	 * Campo de texto donde el usuario escribirá su mensaje.
	 */
	private JTextField campoMensaje;

	/**
	 * Botón para enviar el mensaje escrito en el campo de texto.
	 */
	private JButton botonEnviar;

	/**
	 * Socket para unirse al grupo multicast y enviar/recibir mensajes.
	 */
	private MulticastSocket socket;

	/**
	 * Dirección del grupo multicast al que se conectará el cliente.
	 */
	private InetAddress grupo;

	/**
	 * Nombre del usuario que envía mensajes en el chat.
	 */
	private String nombreUsuario;

	private static final int PUERTO = 9879;
	private static final String IP_GRUPO = "230.0.0.1";

	/**
	 * Constructor principal de la clase ChatCliente. Configura la interfaz gráfica
	 * y el socket Multicast.
	 */
	public ChatCliente() {
		// Solicitar el nombre del usuario al inicio
		nombreUsuario = JOptionPane.showInputDialog(this, "Introduce tu nombre de usuario:", "Nombre de usuario",
				JOptionPane.PLAIN_MESSAGE);
		if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
			nombreUsuario = "UsuarioAnónimo";
		}

		setTitle("Chat Multicast - " + nombreUsuario);
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Área de mensajes (parte superior)
		areaMensajes = new JTextArea();
		areaMensajes.setEditable(false);
		JScrollPane scroll = new JScrollPane(areaMensajes);
		add(scroll, BorderLayout.CENTER);

		// Panel inferior con campo de texto y botón
		JPanel panelInferior = new JPanel(new BorderLayout());
		campoMensaje = new JTextField();
		botonEnviar = new JButton("Enviar");
		panelInferior.add(campoMensaje, BorderLayout.CENTER);
		panelInferior.add(botonEnviar, BorderLayout.EAST);
		add(panelInferior, BorderLayout.SOUTH);

		// Configuración del socket multicast
		try {
			socket = new MulticastSocket(PUERTO);
			grupo = InetAddress.getByName(IP_GRUPO);
			socket.joinGroup(grupo);

			// Hilo para escuchar mensajes
			new Thread(() -> escucharMensajes()).start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al configurar el socket: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		// Acción para el botón enviar
		botonEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMensaje();
			}
		});

		setVisible(true);
	}

	/**
	 * Método para enviar un mensaje al grupo multicast. El mensaje incluye el
	 * nombre del usuario y el texto ingresado.
	 */
	private void enviarMensaje() {
		try {
			String mensaje = campoMensaje.getText().trim();
			if (!mensaje.isEmpty()) {
				mensaje = nombreUsuario + ": " + mensaje; // Añadir nombre del usuario
				byte[] buffer = mensaje.getBytes();
				DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, PUERTO);
				socket.send(paquete);
				campoMensaje.setText("");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Método para escuchar mensajes enviados al grupo multicast. Los mensajes
	 * recibidos se muestran en el área de texto.
	 */
	private void escucharMensajes() {
		try {
			byte[] buffer = new byte[1024];
			while (true) {
				DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
				socket.receive(paquete);
				String mensaje = new String(paquete.getData(), 0, paquete.getLength());
				areaMensajes.append(mensaje + "\n");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al recibir mensaje: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Método principal para iniciar el cliente de chat.
	 * 
	 * @param args Argumentos de la línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ChatCliente::new);
	}
}
