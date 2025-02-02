package com.tareas.pendientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tareas.pendientes.view.ConfiguracionActivity;
import com.tareas.pendientes.view.CrearTareaActivity;
import com.tareas.pendientes.view.LoginActivity;
import com.tareas.pendientes.view.TareasActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVerTareas, btnCrearTarea, btnConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Validar sesión antes de mostrar la pantalla principal
        if (!usuarioAutenticado()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Evita que el usuario vuelva atrás sin iniciar sesión
            return;
        }

        setContentView(R.layout.activity_main);

        inicializarUI();
        configurarEventos();
    }

    private void inicializarUI() {
        btnVerTareas = findViewById(R.id.btnVerTareas);
        btnCrearTarea = findViewById(R.id.btnCrearTarea);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
    }

    private void configurarEventos() {
        btnVerTareas.setOnClickListener(v -> abrirActividad(TareasActivity.class));
        btnCrearTarea.setOnClickListener(v -> abrirActividad(CrearTareaActivity.class));
        btnConfiguracion.setOnClickListener(v -> abrirActividad(ConfiguracionActivity.class));
    }

    private void abrirActividad(Class<?> actividad) {
        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }

    private boolean usuarioAutenticado() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        return prefs.contains("userId"); // Si existe userId, el usuario está logueado
    }
}
