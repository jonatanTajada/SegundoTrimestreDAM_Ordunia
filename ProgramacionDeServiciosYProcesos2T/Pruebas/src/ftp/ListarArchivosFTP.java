package ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class ListarArchivosFTP {
	
    public static void main(String[] args) {
    	
        String servidor = "127.0.0.1";  
        int puerto = 21;
        String usuario = "test";
        String contraseña = "1234";

        FTPClient clienteFTP = new FTPClient();

        try {
            clienteFTP.connect(servidor, puerto);
            System.out.println("Conectado a " + servidor);

            boolean login = clienteFTP.login(usuario, contraseña);
            if (login) {
                System.out.println("Login exitoso!");

                // Obtener la lista de archivos en el directorio actual
                String[] archivos = clienteFTP.listNames();
                if (archivos != null) {
                    System.out.println("Archivos en el servidor:");
                    for (String archivo : archivos) {
                        System.out.println(archivo);
                    }
                } else {
                    System.out.println("No se encontraron archivos.");
                }

                clienteFTP.logout();
            } else {
                System.out.println("Error en el login.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clienteFTP.isConnected()) {
                    clienteFTP.disconnect();
                    System.out.println("Desconectado del servidor.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

