package com.tareas.pendientes.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;

public class MainActivity extends AppCompatActivity {

    private Button btnVerTareas, btnCrearTarea, btnConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarUI();

        btnVerTareas.setOnClickListener(v -> startActivity(new Intent(this, TareasActivity.class)));
        btnCrearTarea.setOnClickListener(v -> startActivity(new Intent(this, CrearTareaActivity.class)));
        btnConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, ConfiguracionActivity.class)));
    }

    private void inicializarUI() {
        btnVerTareas = findViewById(R.id.btnVerTareas);
        btnCrearTarea = findViewById(R.id.btnCrearTarea);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
    }
}
