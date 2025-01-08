package com.ejemplo.binding.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controlador {
    @FXML
    private TextField campoTexto;

    @FXML
    private Label etiqueta;

    @FXML
    public void initialize() {
        // Vinculamos el texto del TextField al texto del Label
        etiqueta.textProperty().bind(campoTexto.textProperty());
    }
}
