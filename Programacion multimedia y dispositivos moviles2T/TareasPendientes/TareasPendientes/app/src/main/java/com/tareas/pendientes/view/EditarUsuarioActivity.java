package com.tareas.pendientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.MainActivity;
import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText etNombre, etEmail;
    private Button btnGuardarCambios, btnCancelar;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        //  Inicializar elementos de la UI
        inicializarUI();

        //  Evento para guardar cambios
        btnGuardarCambios.setOnClickListener(v -> guardarCambios());

        // Botón para cancelar y volver atrás
        btnCancelar.setOnClickListener(v -> finish());
    }

    //  Método para inicializar la interfaz de usuario
    private void inicializarUI() {
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnCancelar = findViewById(R.id.btnCancelar);
        dbHelper = new DatabaseHelper(this);

        // Obtener usuario actual de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // Si hay un usuario logueado, cargar sus datos
        if (userId != -1) {
            cargarDatosUsuario();
        }
    }

    // Método para cargar los datos del usuario desde la base de datos
    private void cargarDatosUsuario() {
        Cursor cursor = dbHelper.obtenerUsuarioPorId(userId);

        if (cursor != null && cursor.moveToFirst()) {
            int colNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE);
            int colEmail = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);

            if (colNombre != -1 && colEmail != -1) {
                String nombre = cursor.getString(colNombre);
                String email = cursor.getString(colEmail);

                // Prellenar los campos con los datos actuales del usuario
                etNombre.setText(nombre);
                etEmail.setText(email);
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    // Método para guardar los cambios en el usuario
    private void guardarCambios() {
        String nuevoNombre = etNombre.getText().toString().trim();
        String nuevoEmail = etEmail.getText().toString().trim();

        //  Validación: No permitir campos vacíos
        if (nuevoNombre.isEmpty() || nuevoEmail.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar el usuario en la base de datos
        boolean actualizado = dbHelper.actualizarUsuario(userId, nuevoNombre, nuevoEmail);

        if (actualizado) {
            // Guardar el nuevo email en la sesión
            SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userEmail", nuevoEmail);
            editor.apply();

            Toast.makeText(this, getString(R.string.usuario_actualizado), Toast.LENGTH_SHORT).show();

            // Redirigir a MainActivity después de la actualización
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_actualizar), Toast.LENGTH_SHORT).show();
        }
    }
}
