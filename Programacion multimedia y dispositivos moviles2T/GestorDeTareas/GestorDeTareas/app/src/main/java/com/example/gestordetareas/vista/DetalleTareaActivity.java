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

    /**
     * Método que se ejecuta cuando se crea la actividad.
     * Se encarga de inicializar las vistas y cargar los datos de la tarea seleccionada.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tarea);

        // Inicializar las vistas del layout
        tvTitulo = findViewById(R.id.tv_titulo);
        tvDescripcion = findViewById(R.id.tv_descripcion);
        tvFecha = findViewById(R.id.tv_fecha);
        ivImagen = findViewById(R.id.iv_imagen);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Cargar los datos enviados por el Intent
        cargarDatosDesdeIntent(getIntent());

        // Configurar botón de volver
        btnVolver.setOnClickListener(v -> finish());
    }

    /**
     * Método que recibe el Intent con los datos de la tarea y los muestra en la interfaz.
     *
     * @param intent Intent que contiene los datos de la tarea seleccionada.
     */
    private void cargarDatosDesdeIntent(Intent intent) {
        String titulo = intent.getStringExtra("TITULO");
        String descripcion = intent.getStringExtra("DESCRIPCION");
        String fecha = intent.getStringExtra("FECHA");
        String imagenUri = intent.getStringExtra("IMAGEN_URI");

        // Log para depuración, muestra los datos obtenidos
        Log.d(TAG, "Cargando detalles: Título=" + titulo + ", Descripción=" + descripcion + ", Fecha=" + fecha + ", Imagen=" + imagenUri);

        // Mostrar los datos en los TextView, validando si están vacíos
        tvTitulo.setText(titulo != null ? titulo : "Sin título");
        tvDescripcion.setText(descripcion != null ? descripcion : "Sin descripción");
        tvDescripcion.setSingleLine(false); // Permite múltiples líneas
        tvDescripcion.setMaxLines(Integer.MAX_VALUE);
        tvFecha.setText(fecha != null ? fecha : "Fecha no disponible");

        //  Cargar la imagen si existe, de lo contrario, mostrar un placeholder
        if (imagenUri != null && !imagenUri.isEmpty()) {
            ivImagen.setImageURI(Uri.parse(imagenUri));
            ivImagen.setTag(imagenUri); // Guarda la URI en la etiqueta de la imagen
        } else {
            ivImagen.setImageResource(R.drawable.imagen_placeholder); // Imagen por defecto
        }
    }
}
