package com.tareas.pendientes.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;


public class EditarTareaActivity extends AppCompatActivity {

    private EditText etTituloEditar, etFechaEditar, etDescripcionEditar;
    private ImageView ivImagenEditar;
    private Button btnGuardarEdicion, btnSeleccionarImagen, btnVolver;
    private DatabaseHelper dbHelper;
    private int tareaId;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        dbHelper = new DatabaseHelper(this);

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
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivImagenEditar.setImageURI(imageUri);
        }
    }

    private void guardarEdicion() {
        String tituloNuevo = etTituloEditar.getText().toString();
        String fechaNueva = etFechaEditar.getText().toString();
        String descripcionNueva = etDescripcionEditar.getText().toString();
        String imagenPath = (imageUri != null) ? imageUri.toString() : null;

        if (tituloNuevo.isEmpty() || fechaNueva.isEmpty() || descripcionNueva.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizado = dbHelper.actualizarTarea(tareaId, tituloNuevo, fechaNueva, descripcionNueva, imagenPath);

        if (actualizado) {
            Toast.makeText(this, getString(R.string.tarea_actualizada), Toast.LENGTH_SHORT).show();

            // Redirigir a MainActivity después de la edición
            Intent intent = new Intent(EditarTareaActivity.this, com.tareas.pendientes.MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_actualizar), Toast.LENGTH_SHORT).show();
        }
    }

}
