package servidor;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ServidorChatGUI extends JFrame {
	

	private static final long serialVersionUID = 3911460067349997490L;
	
	private JTextArea logTextArea;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloUsuarios;
    private JButton btnIniciar;
    private ServidorChat servidor;
    private boolean servidorIniciado = false;

    public ServidorChatGUI() {
        setTitle("Servidor de Chat");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Área de log
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(logTextArea);
        panel.add(scrollLog, BorderLayout.CENTER);

        // Lista de usuarios
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(150, 0));
        panel.add(scrollUsuarios, BorderLayout.EAST);

        // Botón de iniciar/detener
        btnIniciar = new JButton("Iniciar Servidor");
        btnIniciar.addActionListener(e -> {
            if (!servidorIniciado) {
                iniciarServidor();
            } else {
                detenerServidor();
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnIniciar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        add(panel);
    }

    public void iniciarServidor() {
        servidor = new ServidorChat(this);
        btnIniciar.setEnabled(false);

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                servidor.iniciarServidor();
                return null;
            }

            @Override
            protected void done() {
                btnIniciar.setEnabled(true);
                servidorIniciado = false;
            }
        };

        worker.execute();
        servidorIniciado = true;
        btnIniciar.setText("Servidor en ejecución...");
    }

    public void detenerServidor() {
        if (servidor != null) {
            servidor.detenerServidor();
            servidor = null;
            servidorIniciado = false;
            btnIniciar.setText("Iniciar Servidor");
            btnIniciar.setEnabled(true);
            actualizarLog("Servidor detenido.");
        }
    }

    public void actualizarLog(String mensaje) {
        SwingUtilities.invokeLater(() -> logTextArea.append(mensaje + "\n"));
    }

    public void actualizarUsuarios(Set<String> usuarios) {
        SwingUtilities.invokeLater(() -> {
            modeloUsuarios.clear();
            for (String usuario : usuarios) {
                modeloUsuarios.addElement(usuario);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServidorChatGUI ventana = new ServidorChatGUI();
            ventana.setVisible(true);
        });
    }
}
