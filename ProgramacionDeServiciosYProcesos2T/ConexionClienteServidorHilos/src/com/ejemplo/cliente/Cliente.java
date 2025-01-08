package com.ejemplo.cliente;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Cliente {
    private static final String SERVIDOR_IP = "127.0.0.1";
    private static final int PUERTO = 10102;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVIDOR_IP, PUERTO)) {
            System.out.println("Conectado al servidor.");

            // Streams para la comunicación
            OutputStream salida = socket.getOutputStream();
            PrintWriter escritor = new PrintWriter(salida, true);
            InputStream entrada = socket.getInputStream();
            BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));

            // Seleccionar un archivo para enviar
            JFileChooser selectorArchivo = new JFileChooser();
            int seleccion = selectorArchivo.showOpenDialog(null);
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                System.out.println("Operación cancelada.");
                return;
            }

            File archivo = selectorArchivo.getSelectedFile();
            escritor.println(archivo.getName()); // Enviar el nombre del archivo

            // Enviar el contenido del archivo
            FileInputStream fis = new FileInputStream(archivo);
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                salida.write(buffer, 0, bytesLeidos);
            }
            fis.close();
            System.out.println("Archivo enviado: " + archivo.getName());

            // Leer la confirmación del servidor
            String confirmacion = lector.readLine();
            System.out.println("Respuesta del servidor: " + confirmacion);

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
