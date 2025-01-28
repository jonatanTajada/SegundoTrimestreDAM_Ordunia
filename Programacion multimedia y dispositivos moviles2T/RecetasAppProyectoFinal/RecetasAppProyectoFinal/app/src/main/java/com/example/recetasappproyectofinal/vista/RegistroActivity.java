package com.example.recetasappproyectofinal.vista;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Receta;

public class RegistroActivity extends AppCompatActivity {

    private EditText etTitulo, etIngredientes, etInstrucciones;
    private Button btnGuardar;
    private BaseDatos dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dbHelper = new BaseDatos(this);

        etTitulo = findViewById(R.id.et_titulo_receta);
        etIngredientes = findViewById(R.id.et_ingredientes);
        etInstrucciones = findViewById(R.id.et_instrucciones);
        btnGuardar = findViewById(R.id.btn_guardar_receta);

        btnGuardar.setOnClickListener(v -> guardarReceta());
    }

    private void guardarReceta() {
        String titulo = etTitulo.getText().toString().trim();
        String ingredientes = etIngredientes.getText().toString().trim();
        String instrucciones = etInstrucciones.getText().toString().trim();

        if (titulo.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Usuario ID de sesión (reemplázalo por el sistema de autenticación real)
        int usuarioId = 1;

        // Corregimos la inserción
        long resultado = dbHelper.insertarReceta(titulo, ingredientes, instrucciones, usuarioId);

        if (resultado != -1) {
            Toast.makeText(this, "Receta guardada con éxito", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al guardar la receta", Toast.LENGTH_SHORT).show();
        }
    }
}
