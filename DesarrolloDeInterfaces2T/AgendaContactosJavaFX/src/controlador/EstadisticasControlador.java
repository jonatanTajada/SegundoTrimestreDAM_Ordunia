package controlador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.service.ContactoServiceImpl;
import modelo.Contacto;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controlador para la ventana de estadísticas de contactos.
 * Muestra un gráfico de distribución de contactos por localidad y el total de contactos registrados.
 */
public class EstadisticasControlador implements Initializable {

    @FXML private PieChart graficoLocalidades;
    @FXML private Button btnCerrar;
    @FXML private Label lblTotalContactos; 

    private final ContactoServiceImpl contactoService = new ContactoServiceImpl();

    /**
     * Método de inicialización del controlador.
     * Se ejecuta automáticamente al cargar la ventana.
     * @param location URL de la ubicación relativa del archivo FXML.
     * @param resources Recursos internacionales, si los hay.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refrescarEstadisticas(); // Cargar las estadísticas al abrir la ventana.
    }

    /**
     * Refresca las estadísticas de los contactos en tiempo real.
     * Muestra la distribución de contactos por localidad en un gráfico de pastel.
     */
    public void refrescarEstadisticas() {
        List<Contacto> contactos = contactoService.obtenerContactos();
        
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos en la base de datos.");
            mostrarAlerta("No hay contactos en la base de datos.", Alert.AlertType.INFORMATION);
            return;
        }

        // Agrupar contactos por localidad y contar ocurrencias.
        Map<String, Long> frecuencia = contactos.stream()
                .collect(Collectors.groupingBy(c -> 
                    (c.getLocalidad() == null || c.getLocalidad().trim().isEmpty()) ? "Desconocida" : c.getLocalidad(),
                    Collectors.counting()
                ));

        // Calcular el total de contactos.
        long totalContactos = contactos.size();
        lblTotalContactos.setText("Total de Contactos: " + totalContactos);

        // Limpiar el gráfico y agregar nuevos datos.
        graficoLocalidades.getData().clear();
        frecuencia.forEach((localidad, cantidad) -> {
            double porcentaje = (cantidad * 100.0) / totalContactos;
            PieChart.Data data = new PieChart.Data(localidad + " (" + String.format("%.1f", porcentaje) + "%)", cantidad);
            graficoLocalidades.getData().add(data);
        });
    }

    /**
     * Muestra una alerta informativa en la interfaz.
     * @param mensaje Mensaje a mostrar en la alerta.
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR, etc.).
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Cierra la ventana de estadísticas.
     */
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
