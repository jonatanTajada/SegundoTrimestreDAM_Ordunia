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

public class EstadisticasControlador implements Initializable {

    @FXML private PieChart graficoLocalidades;
    @FXML private Button btnCerrar;
    @FXML private Label lblTotalContactos; 

    private final ContactoServiceImpl contactoService = new ContactoServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refrescarEstadisticas(); // ✅ Cargar las estadísticas al abrir la ventana
    }

    /**
     * 🔄 Método para refrescar las estadísticas en tiempo real.
     */
    public void refrescarEstadisticas() {
        List<Contacto> contactos = contactoService.obtenerContactos();
        
        if (contactos.isEmpty()) {
            System.out.println("⚠ No hay contactos en la base de datos.");
            mostrarAlerta("No hay contactos en la base de datos.", Alert.AlertType.INFORMATION);
            return;
        }

        // 🔹 Agrupar por localidad y contar ocurrencias
        Map<String, Long> frecuencia = contactos.stream()
                .collect(Collectors.groupingBy(c -> 
                    (c.getLocalidad() == null || c.getLocalidad().trim().isEmpty()) ? "Desconocida" : c.getLocalidad(),
                    Collectors.counting()
                ));

        // 🔹 Calcular el total de contactos
        long totalContactos = contactos.size();
        lblTotalContactos.setText("Total de Contactos: " + totalContactos);

        // 🔄 Limpiar el gráfico y agregar nuevos datos
        graficoLocalidades.getData().clear();
        frecuencia.forEach((localidad, cantidad) -> {
            double porcentaje = (cantidad * 100.0) / totalContactos;
            PieChart.Data data = new PieChart.Data(localidad + " (" + String.format("%.1f", porcentaje) + "%)", cantidad);
            graficoLocalidades.getData().add(data);
        });
    }

    /**
     * 📌 Método para mostrar alertas informativas.
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * ❌ Cerrar la ventana de estadísticas.
     */
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
