package com.figurasgeometricas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // === CREACIÓN DE LAS FIGURAS ===

        // Rectángulo (Rojo por defecto)
        Rectangle rectangulo = new Rectangle(200, 100, Color.RED);
        rectangulo.setX(50);
        rectangulo.setY(50);

        // Círculo (Verde por defecto)
        Circle circulo = new Circle(50, Color.GREEN);
        circulo.setCenterX(200);
        circulo.setCenterY(300);

        // Triángulo (Negro por defecto)
        Polygon triangulo = new Polygon();
        triangulo.getPoints().addAll(
            400.0, 400.0,  // Punto superior
            500.0, 500.0,  // Punto inferior derecho
            300.0, 500.0   // Punto inferior izquierdo
        );
        triangulo.setFill(Color.BLACK);

        // === MENÚ CONTEXTUAL PARA EL RECTÁNGULO ===
        ContextMenu menuContextual = new ContextMenu();

        // Opción para cambiar a rojo
        MenuItem opcionRojo = new MenuItem("Cambiar a Rojo");
        opcionRojo.setOnAction(event -> rectangulo.setFill(Color.RED));

        // Opción para cambiar a azul
        MenuItem opcionAzul = new MenuItem("Cambiar a Azul");
        opcionAzul.setOnAction(event -> rectangulo.setFill(Color.BLUE));

        // Añadir las opciones al menú contextual
        menuContextual.getItems().addAll(opcionRojo, opcionAzul);

        // Asociar el menú contextual al rectángulo
        rectangulo.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) { // Detectar clic derecho
                menuContextual.show(rectangulo, event.getScreenX(), event.getScreenY());
            }
        });

        // === EVENTOS PARA EL CÍRCULO ===
        circulo.setOnMouseEntered(event -> circulo.setFill(Color.YELLOW)); // Al pasar el ratón
        circulo.setOnMouseExited(event -> circulo.setFill(Color.GREEN));   // Al salir el ratón

        // === EVENTOS PARA EL TRIÁNGULO ===
        triangulo.setOnMousePressed(event -> triangulo.setScaleX(1.5));   // Escala al presionar
        triangulo.setOnMouseReleased(event -> triangulo.setScaleX(1.0));  // Vuelve al tamaño normal

        // === AÑADIR FIGURAS AL PANEL ===
        root.getChildren().addAll(rectangulo, circulo, triangulo);

        // === CONFIGURAR LA ESCENA Y MOSTRARLA ===
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Figuras Geométricas con Eventos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
