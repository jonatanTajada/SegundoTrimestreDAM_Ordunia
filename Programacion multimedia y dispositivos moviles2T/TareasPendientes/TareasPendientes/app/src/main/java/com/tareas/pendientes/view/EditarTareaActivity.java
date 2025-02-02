package com.tareas.pendientes.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;

public class EditarTareaActivity extends AppCompatActivity {

    private TextView etTituloEditar, etFechaEditar, etDescripcionEditar; // Cambié EditText por TextView donde corresponde
    private ImageView ivImagenEditar;
    private Button btnGuardarEdicion, btnSeleccionarImagen, btnVolver;
    private DatabaseHelper dbHelper;
    private int tareaId;
    private Uri imageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    ivImagenEditar.setImageURI(imageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        dbHelper = new DatabaseHelper(this);

        // Inicializar UI
        etTituloEditar = findViewById(R.id.etTituloEditar);
        etFechaEditar = findViewById(R.id.etFechaEditar);
        etDescripcionEditar = findViewById(R.id.etDescripcionEditar);
        ivImagenEditar = findViewById(R.id.ivImagenEditar);
        btnGuardarEdicion = findViewById(R.id.btnGuardarEdicion);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener datos de la tarea desde el intent
        Intent intent = getIntent();
        tareaId = intent.getIntExtra("ID_TAREA", -1);
        etTituloEditar.setText(intent.getStringExtra("TITULO"));
        etFechaEditar.setText(intent.getStringExtra("FECHA"));
        etDescripcionEditar.setText(intent.getStringExtra("DESCRIPCION"));

        String imagenGuardada = intent.getStringExtra("IMAGEN");
        if (imagenGuardada != null && !imagenGuardada.isEmpty()) {
            ivImagenEditar.setImageURI(Uri.parse(imagenGuardada));
        }

        btnSeleccionarImagen.setOnClickListener(v -> abrirSelectorImagen());
        btnGuardarEdicion.setOnClickListener(v -> guardarEdicion());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void abrirSelectorImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void guardarEdicion() {
        String tituloNuevo = etTituloEditar.getText().toString().trim();
        String fechaNueva = etFechaEditar.getText().toString().trim();
        String descripcionNueva = etDescripcionEditar.getText().toString().trim();
        String imagenPath = (imageUri != null) ? imageUri.toString() : null;

        if (tituloNuevo.isEmpty() || fechaNueva.isEmpty() || descripcionNueva.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizado = dbHelper.actualizarTarea(tareaId, tituloNuevo, fechaNueva, descripcionNueva, imagenPath);

        if (actualizado) {
            Toast.makeText(this, getString(R.string.tarea_actualizada), Toast.LENGTH_SHORT).show();

            // Redirigir correctamente a la lista de tareas en lugar de MainActivity
            Intent intent = new Intent(EditarTareaActivity.this, TareasActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_actualizar), Toast.LENGTH_SHORT).show();
        }
    }
}
