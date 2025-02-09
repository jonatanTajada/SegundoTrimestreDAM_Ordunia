package servidor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Set;
import javax.swing.*;

import utilidades.EstilosUI;

public class ServidorChatGUI extends JFrame {

    private static final long serialVersionUID = 3911460067349997490L;

    private JTextArea logTextArea;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloUsuarios;
    private JButton btnIniciar;
    private ServidorChat servidor;
    private boolean servidorIniciado = false;

    public ServidorChatGUI() {
        // **Aplicar Estilos Globales**
        EstilosUI.aplicarEstilosGenerales();
        EstilosUI.aplicarSombraVentana(this);
        EstilosUI.estilizarTitulo(this);

        setTitle("Servidor de Chat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // **Panel Principal**
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        EstilosUI.estilizarPanel(panelPrincipal, false); // **Fondo claro**

        // **Área de Log**
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(logTextArea);
        panelPrincipal.add(scrollLog, BorderLayout.CENTER);
        EstilosUI.estilizarAreaTexto(logTextArea, false);

        // **Panel Derecho - Lista de Usuarios**
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        JLabel labelUsuarios = new JLabel("Usuarios Conectados", JLabel.CENTER);
        EstilosUI.estilizarEtiqueta(labelUsuarios);
        panelUsuarios.add(labelUsuarios, BorderLayout.NORTH);

        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(150, 0));
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
        panelPrincipal.add(panelUsuarios, BorderLayout.EAST);

        // **Botón de Iniciar/Detener Servidor**
        btnIniciar = new JButton("Iniciar Servidor");
        EstilosUI.estilizarBoton(btnIniciar);

        btnIniciar.addActionListener(e -> {
            if (!servidorIniciado) {
                iniciarServidor();
            } else {
                detenerServidor();
            }
        });

        // **Panel del Botón**
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnIniciar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // **Aplicar Estilos**
        EstilosUI.estilizarPanel(panelPrincipal, false); // Fondo claro
        EstilosUI.estilizarPanel(panelUsuarios, false);
        EstilosUI.estilizarPanel(panelBoton, false);
        EstilosUI.estilizarBoton(btnIniciar);

        add(panelPrincipal);
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
