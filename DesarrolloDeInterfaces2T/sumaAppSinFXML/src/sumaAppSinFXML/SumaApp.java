package sumaAppSinFXML;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

		//******SIN FXML*****************

public class SumaApp extends Application {
	
    @Override
    public void start(Stage primaryStage) {
    	
        TextField operando1 = new TextField();
        TextField operando2 = new TextField();
        Button sumar = new Button("Sumar");
        TextField resultado = new TextField();
        resultado.setEditable(false);

        
        sumar.setOnAction(e -> {
        	
        	try {
        		
				double num1 = Double.parseDouble(operando1.getText());
				double num2 = Double.parseDouble(operando2.getText());
        		
				double suma = num1 + num2;
				
				resultado.setText(String.valueOf(suma));
        		
			} catch (NumberFormatException e2) {
				resultado.setText("Ocurrio algun tipo de error, intentelo de nuevo, con valores numericos: " + e2.getMessage());
			}
        	
        });
        
        
        
        VBox layout = new VBox(10, operando1, operando2, sumar, resultado);
        Scene scene = new Scene(layout, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

