package com.example.gestordetareas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DashboardActivity extends AppCompatActivity {

    private Button btnVerTareas, btnAgregarTarea, btnAjustes, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referencias a los botones
        btnVerTareas = findViewById(R.id.btn_ver_tareas);
        btnAgregarTarea = findViewById(R.id.btn_agregar_tarea);
        btnAjustes = findViewById(R.id.btn_ajustes);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_dashboard); // Botón cerrar sesión

        // Ir a la pantalla de Ver Tareas
        btnVerTareas.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, ListaTareasActivity.class))
        );

        // Ir a la pantalla de Agregar Tarea
        btnAgregarTarea.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AgregarTareaActivity.class))
        );

        // Ir a Ajustes
        btnAjustes.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AjustesActivity.class))
        );

        // Cerrar Sesión con confirmación
        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion());
    }

    // Método para mostrar el diálogo de confirmación
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Aplicación")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> finishAffinity()) // Cierra la app completamente
                .setNegativeButton("No", null) // No hace nada si el usuario cancela
                .show();
    }
}
