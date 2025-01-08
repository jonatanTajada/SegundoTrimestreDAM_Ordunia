package controlador;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import modelo.Contador;

public class ControladorGasolinera {
    @FXML
    private TextField campoCantidadEuros;
    @FXML
    private TextField campoGasolina;
    @FXML
    private ProgressBar barraProgreso;
    @FXML
    private Button botonIniciar;
    @FXML
    private Button botonCancelar;

    private Contador contador;
    private Thread hiloCarga;

    // 1€/L
    private final double PRECIO_POR_LITRO = 1.5;

    @FXML
    public void initialize() {
    	
        contador = new Contador();
        
        botonIniciar.setOnAction(e -> iniciarCarga());
        botonCancelar.setOnAction(e -> cancelarCarga());
    }

    private void iniciarCarga() {
        // Obtener el valor en euros ingresado por el usuario
        String textoEuros = campoCantidadEuros.getText();
        
        if (textoEuros.isEmpty()) {
            campoGasolina.setText("Introduce euros");
            return;
        }
        
        try {
            double euros = Double.parseDouble(textoEuros);
            double litrosTotales = euros / PRECIO_POR_LITRO; 
            contador.setValor(0); 

            // Inicializar la barra de progreso
            barraProgreso.setProgress(0);

            // Iniciar un hilo para simular la carga progresiva
            hiloCarga = new Thread(() -> {
                for (double i = 0; i <= litrosTotales; i++) {
                    final double litrosActual = i;
                    final double progreso = litrosActual / litrosTotales;

                    Platform.runLater(() -> {
                        campoGasolina.setText(String.format("%.2f", litrosActual));
                        barraProgreso.setProgress(progreso);
                    });

                    try {
                        Thread.sleep(500); // Simula el tiempo de carga, medio segundo por litro
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            });

            hiloCarga.setDaemon(true);
            hiloCarga.start();

        } catch (NumberFormatException ex) {
            campoGasolina.setText("Numero invalido");
        }
    }

    private void cancelarCarga() {
    	
        if (hiloCarga != null && hiloCarga.isAlive()) {
            hiloCarga.interrupt();
        }
        campoCantidadEuros.setText("");
        campoGasolina.setText("");
        barraProgreso.setProgress(0);
    }
}
