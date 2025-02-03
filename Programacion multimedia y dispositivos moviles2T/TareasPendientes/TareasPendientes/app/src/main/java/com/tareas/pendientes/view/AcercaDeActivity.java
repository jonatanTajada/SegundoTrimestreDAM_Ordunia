package com.tareas.pendientes.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;
import com.tareas.pendientes.BuildConfig;

public class AcercaDeActivity extends AppCompatActivity {

    private TextView tvVersion;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        // Inicializar elementos de la UI
        inicializarUI();

        // Mostrar la versión de la aplicación
        String versionName = BuildConfig.VERSION_NAME;
        tvVersion.setText(getString(R.string.version_app, versionName));

        // Configurar el botón para volver atrás
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para inicializar los elementos de la interfaz
    private void inicializarUI() {
        tvVersion = findViewById(R.id.tvVersion);
        btnVolver = findViewById(R.id.btnVolver);
    }
}
