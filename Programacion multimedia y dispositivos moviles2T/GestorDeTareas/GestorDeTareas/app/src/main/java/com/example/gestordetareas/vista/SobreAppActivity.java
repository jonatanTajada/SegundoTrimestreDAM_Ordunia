package com.example.gestordetareas.vista;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.gestordetareas.R;

public class SobreAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_app);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.sobre_app_titulo)); // Asigna el título correcto
        }

        // Botón Volver
        Button btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(view -> finish()); // Cierra la actividad y regresa
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Botón de retroceso en la Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
