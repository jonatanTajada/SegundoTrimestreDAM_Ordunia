package com.tareas.pendientes.view;

import android.content.Context;
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
import com.tareas.pendientes.utils.Encriptador;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegistro;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar la base de datos y preferencias
        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        // Si el usuario ya está logueado, lo redirigimos al MainActivity
        if (sharedPreferences.getBoolean("logueado", false)) {
            irAlMain();
        }

        inicializarUI();

        // Evento para validar usuario al hacer clic en "Login"
        btnLogin.setOnClickListener(v -> validarUsuario());

        // Evento para ir a la pantalla de registro
        btnRegistro.setOnClickListener(v -> irARegistro());
    }

    // Método para inicializar los elementos de la interfaz
    private void inicializarUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
    }

    // Método para validar el usuario ingresado
    private void validarUsuario() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Verificación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensaje(getString(R.string.error_campos_obligatorios));
            return;
        }

        // Consultar usuario en la base de datos
        Cursor cursor = dbHelper.obtenerUsuarioPorEmail(email);

        if (cursor != null && cursor.moveToFirst()) {
            int colPassword = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            int colUserId = cursor.getColumnIndex(DatabaseHelper.COLUMN_USUARIO_ID);

            if (colPassword != -1 && colUserId != -1) {
                String passwordAlmacenada = cursor.getString(colPassword);
                int userId = cursor.getInt(colUserId);

                // Verificar contraseña encriptada
                if (Encriptador.verificarPassword(password, passwordAlmacenada)) {
                    guardarSesion(userId, email);
                    mostrarMensaje(getString(R.string.login_exitoso));

                    // Redirigir al MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    mostrarMensaje(getString(R.string.error_password));
                }
            } else {
                mostrarMensaje(getString(R.string.error_usuario_no_encontrado));
            }
        } else {
            mostrarMensaje(getString(R.string.error_usuario_no_encontrado));
        }

        if (cursor != null) cursor.close();
    }

    // Método para guardar los datos del usuario en SharedPreferences
    private void guardarSesion(int userId, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.putString("userEmail", email);
        editor.putBoolean("logueado", true);
        editor.apply();
    }

    // Método para redirigir a MainActivity si ya está logueado
    private void irAlMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // Método para redirigir a la pantalla de registro
    private void irARegistro() {
        startActivity(new Intent(this, RegistroActivity.class));
        finish();
    }

    // Método para mostrar mensajes de error o éxito
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
