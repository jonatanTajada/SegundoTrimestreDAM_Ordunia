package udp.cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteHoraUDP {
	
    public static void main(String[] args) {
    	
        try {
        	
            // Crear el socket del cliente
            DatagramSocket clienteSocket = new DatagramSocket();

            // Configurar tiempo de espera de 5000 ms
            clienteSocket.setSoTimeout(5000);

            // Dirección IP del servidor y puerto
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 9878;

            // Crear un mensaje de solicitud vacío
            String mensaje = "SOLICITUD_HORA";
            byte[] bufferEnviar = mensaje.getBytes();

            // Crear paquete para enviar al servidor
            DatagramPacket paqueteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, direccionServidor, puertoServidor);
            clienteSocket.send(paqueteEnviar);
            System.out.println("Solicitud enviada al servidor.");

            // Buffer para recibir la respuesta del servidor
            byte[] bufferRecibir = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);

            // Intentar recibir la respuesta del servidor
            try {
                clienteSocket.receive(paqueteRecibido);

                // Mostrar la respuesta del servidor
                String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            } catch (Exception e) {
                System.err.println("No se recibió respuesta del servidor dentro del tiempo límite.");
            }

            // Cerrar el socket
            clienteSocket.close();
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
