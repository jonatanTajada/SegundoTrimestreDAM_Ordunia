package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
    	
        try {
        	
        	Parent root = FXMLLoader.load(getClass().getResource("/SumaApp.fxml"));
        	
            primaryStage.setTitle("Suma con FXML");
            primaryStage.setScene(new Scene(root, 300, 200));
            primaryStage.show();       
        } catch (Exception e) {
        	
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
