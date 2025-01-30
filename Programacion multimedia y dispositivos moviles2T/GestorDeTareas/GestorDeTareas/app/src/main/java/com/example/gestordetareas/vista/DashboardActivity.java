package com.example.gestordetareas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.gestordetareas.R;

public class DashboardActivity extends AppCompatActivity {

    private Button btnVerTareas, btnAgregarTarea, btnAjustes, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a los botones
        btnVerTareas = findViewById(R.id.btn_ver_tareas);
        btnAgregarTarea = findViewById(R.id.btn_agregar_tarea);
        btnAjustes = findViewById(R.id.btn_ajustes);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_dashboard);

        // Navegación entre actividades
        btnVerTareas.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, ListaTareasActivity.class))
        );

        btnAgregarTarea.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AgregarTareaActivity.class))
        );

        btnAjustes.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AjustesActivity.class))
        );

        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion());
    }

    // Habilitar botón de retroceso en la Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para mostrar el diálogo de confirmación
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Aplicación")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> finishAffinity()) // Cierra la app completamente
                .setNegativeButton("No", null)
                .show();
    }
}
