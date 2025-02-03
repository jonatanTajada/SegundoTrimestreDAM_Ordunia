package com.tareas.pendientes.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;

public class DetalleTareaActivity extends AppCompatActivity {

    private TextView tvTitulo, tvFecha, tvDescripcion;
    private ImageView ivImagenDetalle;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tarea);

        // Inicializar elementos de la UI
        inicializarUI();

        //  Obtener datos de la tarea desde el Intent
        cargarDatosTarea();

        // Configurar el botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para inicializar los elementos de la UI
    private void inicializarUI() {
        tvTitulo = findViewById(R.id.tvTitulo);
        tvFecha = findViewById(R.id.tvFecha);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        ivImagenDetalle = findViewById(R.id.ivImagenDetalle);
        btnVolver = findViewById(R.id.btnVolver);
    }

    // Método para cargar los datos de la tarea recibidos desde el Intent
    private void cargarDatosTarea() {
        Intent intent = getIntent();
        tvTitulo.setText(intent.getStringExtra("TITULO"));
        tvFecha.setText(intent.getStringExtra("FECHA"));
        tvDescripcion.setText(intent.getStringExtra("DESCRIPCION"));

        //  Cargar la imagen si existe, de lo contrario, mostrar imagen por defecto
        String imagenUri = intent.getStringExtra("IMAGEN");
        if (imagenUri != null && !imagenUri.isEmpty()) {
            ivImagenDetalle.setImageURI(Uri.parse(imagenUri));
        } else {
            ivImagenDetalle.setImageResource(R.drawable.ic_placeholder);
        }
    }
}
