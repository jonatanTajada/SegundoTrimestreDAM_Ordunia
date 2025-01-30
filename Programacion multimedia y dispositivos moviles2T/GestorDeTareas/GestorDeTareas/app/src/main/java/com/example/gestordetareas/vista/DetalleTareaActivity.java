package com.example.gestordetareas.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordetareas.R;

public class DetalleTareaActivity extends AppCompatActivity {

    private static final String TAG = "DetalleTareaActivity";
    private TextView tvTitulo, tvDescripcion, tvFecha;
    private ImageView ivImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tarea);

        // Inicializar vistas
        tvTitulo = findViewById(R.id.tv_titulo);
        tvDescripcion = findViewById(R.id.tv_descripcion);
        tvFecha = findViewById(R.id.tv_fecha);
        ivImagen = findViewById(R.id.iv_imagen);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Obtener datos del Intent
        cargarDatosDesdeIntent(getIntent());

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para actualizar los datos en la vista de detalles
    private void cargarDatosDesdeIntent(Intent intent) {
        String titulo = intent.getStringExtra("TITULO");
        String descripcion = intent.getStringExtra("DESCRIPCION");
        String fecha = intent.getStringExtra("FECHA");
        String imagenUri = intent.getStringExtra("IMAGEN_URI");

        Log.d(TAG, "Cargando detalles: Título=" + titulo + ", Descripción=" + descripcion + ", Fecha=" + fecha + ", Imagen=" + imagenUri);

        tvTitulo.setText(titulo != null ? titulo : "Sin título");
        tvDescripcion.setText(descripcion != null ? descripcion : "Sin descripción");
        tvDescripcion.setSingleLine(false);
        tvDescripcion.setMaxLines(Integer.MAX_VALUE);
        tvFecha.setText(fecha != null ? fecha : "Fecha no disponible");

        if (imagenUri != null && !imagenUri.isEmpty()) {
            ivImagen.setImageURI(Uri.parse(imagenUri));
            ivImagen.setTag(imagenUri);
        } else {
            ivImagen.setImageResource(R.drawable.imagen_placeholder);
        }
    }
}
