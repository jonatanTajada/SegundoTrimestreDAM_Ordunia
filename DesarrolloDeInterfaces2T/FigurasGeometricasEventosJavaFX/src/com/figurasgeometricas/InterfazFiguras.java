package com.figurasgeometricas;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class InterfazFiguras extends Pane {
    public InterfazFiguras() {
        // Crear un rectángulo
        Rectangle rectangulo = new Rectangle(200, 100, Color.RED);
        rectangulo.setX(50);
        rectangulo.setY(50);

        // Crear un círculo
        Circle circulo = new Circle(50, Color.GREEN);
        circulo.setCenterX(150);
        circulo.setCenterY(250);

        // Crear un triángulo
        Polygon triangulo = new Polygon();
        triangulo.getPoints().addAll(
            250.0, 300.0,
            300.0, 400.0,
            200.0, 400.0
        );
        triangulo.setFill(Color.BLACK);

        // === AÑADIR EVENTOS ===

        // Evento para cambiar el color del rectángulo
        rectangulo.setOnMouseClicked(event -> {
            if (event.isSecondaryButtonDown()) { // Detecta clic derecho
                rectangulo.setFill(rectangulo.getFill() == Color.RED ? Color.BLUE : Color.RED);
            }
        });



        // Eventos para el círculo
        circulo.setOnMouseEntered(event -> circulo.setFill(Color.YELLOW)); // Cambia a amarillo al pasar el ratón
        circulo.setOnMouseExited(event -> circulo.setFill(Color.GREEN));   // Vuelve a verde al salir el ratón

        // Eventos para el triángulo
        triangulo.setOnMousePressed(event -> triangulo.setScaleX(1.5)); // Escala al presionar
        triangulo.setOnMouseReleased(event -> triangulo.setScaleX(1.0)); // Vuelve al tamaño normal al soltar

        // Añadir las figuras al panel
        this.getChildren().addAll(rectangulo, circulo, triangulo);
    }
}
