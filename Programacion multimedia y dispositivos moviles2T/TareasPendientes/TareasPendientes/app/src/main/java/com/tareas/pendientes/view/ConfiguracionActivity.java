package com.tareas.pendientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnAcercaDe, btnCambiarTema, btnEditarUsuario, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        inicializarUI();

        btnAcercaDe.setOnClickListener(v -> startActivity(new Intent(this, AcercaDeActivity.class)));

        btnCambiarTema.setOnClickListener(v -> cambiarTema());

        btnEditarUsuario.setOnClickListener(v -> startActivity(new Intent(this, EditarUsuarioActivity.class)));

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    private void inicializarUI() {
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnCambiarTema = findViewById(R.id.btnCambiarTema);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
    }

    private void cambiarTema() {
        // Lógica para alternar entre modo claro y oscuro
    }

    private void cerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
