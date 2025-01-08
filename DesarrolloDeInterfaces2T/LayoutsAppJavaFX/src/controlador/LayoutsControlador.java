package controlador;



import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LayoutsControlador {

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
    	
        // Cargar imagen 
        Image image = new Image(getClass().getResourceAsStream("/vista/java.jpg"));
        imageView.setImage(image);
    }
}
