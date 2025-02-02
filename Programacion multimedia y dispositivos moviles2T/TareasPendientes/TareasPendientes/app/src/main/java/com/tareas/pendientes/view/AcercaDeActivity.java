package com.tareas.pendientes.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;
import com.tareas.pendientes.BuildConfig; // ✅ Importación correcta

public class AcercaDeActivity extends AppCompatActivity {

    private TextView tvVersion;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        inicializarUI();

        // Mostrar versión de la app
        String versionName = BuildConfig.VERSION_NAME;
        tvVersion.setText(getString(R.string.version_app, versionName));

        // Botón para volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void inicializarUI() {
        tvVersion = findViewById(R.id.tvVersion);
        btnVolver = findViewById(R.id.btnVolver);
    }
}
