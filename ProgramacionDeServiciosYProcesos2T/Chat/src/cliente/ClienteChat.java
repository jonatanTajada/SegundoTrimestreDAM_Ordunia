package cliente;

import javax.swing.*;
import utilidades.EstilosUI;

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
		// **Aplicar Estilos Generales**
		EstilosUI.aplicarEstilosGenerales();
		EstilosUI.aplicarSombraVentana(this);
		EstilosUI.estilizarTitulo(this);

		// **Panel Principal**
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		EstilosUI.estilizarPanel(panelPrincipal, false);

		// **Área de Chat**
		areaChat = new JTextArea();
		areaChat.setEditable(false);
		EstilosUI.estilizarAreaTexto(areaChat, false);
		JScrollPane scrollChat = new JScrollPane(areaChat);
		panelPrincipal.add(scrollChat, BorderLayout.CENTER);

		// **Panel Derecho con Lista de Usuarios**
		JPanel panelUsuarios = new JPanel(new BorderLayout());
		JLabel labelUsuarios = new JLabel("Usuarios Conectados", JLabel.CENTER);
		EstilosUI.estilizarEtiqueta(labelUsuarios);
		modeloUsuarios = new DefaultListModel<>();
		listaUsuarios = new JList<>(modeloUsuarios);
		JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
		scrollUsuarios.setPreferredSize(new Dimension(150, 0));

		panelUsuarios.add(labelUsuarios, BorderLayout.NORTH);
		panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
		panelPrincipal.add(panelUsuarios, BorderLayout.EAST);

		// **Campo de Mensaje y Botón Enviar**
		JPanel panelInferior = new JPanel(new BorderLayout());
		campoMensaje = new JTextField();
		EstilosUI.estilizarCampoTexto(campoMensaje, false);

		btnEnviar = new JButton("Enviar");
		EstilosUI.estilizarBoton(btnEnviar);

		panelInferior.add(campoMensaje, BorderLayout.CENTER);
		panelInferior.add(btnEnviar, BorderLayout.EAST);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		add(panelPrincipal);

		// **Eventos**
		btnEnviar.addActionListener(e -> enviarMensaje());
		campoMensaje.addActionListener(e -> enviarMensaje());

		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String usuarioSeleccionado = listaUsuarios.getSelectedValue();
					if (usuarioSeleccionado != null && !usuarioSeleccionado.equals(nombreUsuario)) {
						abrirChatPrivado(usuarioSeleccionado);
					}
				}
			}
		});

		conectarAlServidor();

		setTitle("Chat Cliente");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void conectarAlServidor() {
		try {
			String servidor = JOptionPane.showInputDialog(this, "Ingrese la dirección IP del servidor:", "127.0.0.1");
			String puertoStr = JOptionPane.showInputDialog(this, "Ingrese el puerto del servidor:", "5003");
			int puerto = Integer.parseInt(puertoStr);

			socket = new Socket(servidor, puerto);
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			salida = new PrintWriter(socket.getOutputStream(), true);

			nombreUsuario = JOptionPane.showInputDialog(this, "Ingrese su nombre de usuario:");
			if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nombre de usuario no válido. Saliendo...", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

			setTitle("Chat Cliente: " + nombreUsuario);
			salida.println(nombreUsuario);

			new Thread(() -> {
				try {
					String mensaje;
					while ((mensaje = entrada.readLine()) != null) {
						if (mensaje.startsWith("[Usuarios]")) {
							actualizarListaUsuarios(mensaje.substring(10));
						} else if (mensaje.startsWith("[Privado]")) {
							manejarMensajePrivado(mensaje);
						} else {
							if (mensaje.startsWith(nombreUsuario + ":")) {
								mensaje = mensaje.replaceFirst(nombreUsuario + ":", "#Tú:");
							}
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
				if (!usuario.equals(nombreUsuario)) {
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
		String[] partes = mensaje.split(" ", 3);
		if (partes.length < 3)
			return;

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
