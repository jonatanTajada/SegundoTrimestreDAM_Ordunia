package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ClientePrivado extends JFrame {
	

	private static final long serialVersionUID = -2059344554844977256L;

	private JTextArea areaChatPrivado;
    private JTextField campoMensajePrivado;
    private JButton btnEnviarPrivado;
    private String usuarioDestino;
    private PrintWriter salida;

    public ClientePrivado(String usuarioDestino, PrintWriter salida) {
        this.usuarioDestino = usuarioDestino;
        this.salida = salida;

        setTitle("Chat Privado con " + usuarioDestino);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Área de chat privado
        areaChatPrivado = new JTextArea();
        areaChatPrivado.setEditable(false);
        JScrollPane scrollChat = new JScrollPane(areaChatPrivado);
        panel.add(scrollChat, BorderLayout.CENTER);

        // Campo de mensaje y botón enviar
        JPanel panelInferior = new JPanel(new BorderLayout());
        campoMensajePrivado = new JTextField();
        btnEnviarPrivado = new JButton("Enviar");

        panelInferior.add(campoMensajePrivado, BorderLayout.CENTER);
        panelInferior.add(btnEnviarPrivado, BorderLayout.EAST);
        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        // Evento para enviar mensajes privados
        btnEnviarPrivado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensajePrivado();
            }
        });

        campoMensajePrivado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensajePrivado();
            }
        });
    }

    private void enviarMensajePrivado() {
        String mensaje = campoMensajePrivado.getText().trim();
        if (!mensaje.isEmpty()) {
            salida.println("@" + usuarioDestino + " " + mensaje);
            areaChatPrivado.append("Tú: " + mensaje + "\n");
            campoMensajePrivado.setText("");
        }
    }

    public void recibirMensajePrivado(String mensaje) {
        areaChatPrivado.append(mensaje + "\n");
    }
}
