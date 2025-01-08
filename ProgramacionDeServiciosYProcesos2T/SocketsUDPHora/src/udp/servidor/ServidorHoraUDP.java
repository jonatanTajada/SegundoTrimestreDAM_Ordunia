package udp.servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorHoraUDP {
    public static void main(String[] args) {
    	
        try {
        	
            // Crear el socket del servidor en el puerto 9878
            DatagramSocket servidorSocket = new DatagramSocket(9878);
            System.out.println("Servidor UDP (Hora) escuchando en el puerto 9878...");

            // Crear buffer para recibir datos
            byte[] bufferRecibir = new byte[1024];
            byte[] bufferEnviar;

            while (true) {
            	
                // Recibir solicitud del cliente
                DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);
                servidorSocket.receive(paqueteRecibido);

                System.out.println("Solicitud recibida del cliente.");

                // Obtener la fecha y hora actuales
                LocalDateTime ahora = LocalDateTime.now();
                String fechaHora = ahora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                System.out.println("Enviando fecha y hora: " + fechaHora);

                // Preparar el mensaje de respuesta
                bufferEnviar = fechaHora.getBytes();

                // Enviar la respuesta al cliente
                DatagramPacket paqueteEnviar = new DatagramPacket(
                    bufferEnviar,
                    bufferEnviar.length,
                    paqueteRecibido.getAddress(),
                    paqueteRecibido.getPort()
                );
                servidorSocket.send(paqueteEnviar);

                System.out.println("Fecha y hora enviadas al cliente.");
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
