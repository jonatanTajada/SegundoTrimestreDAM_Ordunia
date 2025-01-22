package com.gestorventasapp.app;

import com.gestorventasapp.view.*;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaPrincipal();
        });
    }
}
