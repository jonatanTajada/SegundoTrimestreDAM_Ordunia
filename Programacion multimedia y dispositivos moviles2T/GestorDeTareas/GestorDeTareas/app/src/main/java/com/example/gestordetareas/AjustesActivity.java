package com.example.gestordetareas;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

public class AjustesActivity extends AppCompatActivity {

    private Button btnCambiarTema, btnEliminarTareas, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a los botones
        btnCambiarTema = findViewById(R.id.btn_cambiar_tema);
        btnEliminarTareas = findViewById(R.id.btn_eliminar_tareas);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_ajustes); // Nuevo botón

        // Cambiar tema (por ahora solo mensaje)
        btnCambiarTema.setOnClickListener(view ->
                Toast.makeText(this, "Funcionalidad de cambio de tema aún no implementada", Toast.LENGTH_SHORT).show()
        );

        // Eliminar todas las tareas
        btnEliminarTareas.setOnClickListener(view -> eliminarTodasLasTareas());

        // Cerrar Sesión con confirmación
        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

    // Método para eliminar todas las tareas
    private void eliminarTodasLasTareas() {
        SharedPreferences prefs = getSharedPreferences("MisTareas", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Todas las tareas han sido eliminadas", Toast.LENGTH_SHORT).show();
    }
}
