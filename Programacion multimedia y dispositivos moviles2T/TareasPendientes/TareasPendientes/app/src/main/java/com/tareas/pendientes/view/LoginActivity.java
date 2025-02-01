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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarUI();

        btnLogin.setOnClickListener(v -> validarUsuario());

        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
            finish(); // Cerramos Login para que no pueda volver atrás después de registrarse
        });
    }

    private void inicializarUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        dbHelper = new DatabaseHelper(this);
    }

    private void validarUsuario() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensaje(getString(R.string.error_campos_obligatorios));
            return;
        }

        Cursor cursor = dbHelper.obtenerUsuarioPorEmail(email);

        if (cursor != null && cursor.moveToFirst()) {
            int colPassword = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            int colUserId = cursor.getColumnIndex(DatabaseHelper.COLUMN_USUARIO_ID);

            if (colPassword != -1 && colUserId != -1) {
                String passwordAlmacenada = cursor.getString(colPassword);
                int userId = cursor.getInt(colUserId);

                if (Encriptador.verificarPassword(password, passwordAlmacenada)) {
                    guardarSesion(userId);
                    mostrarMensaje(getString(R.string.login_exitoso));

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

    private void guardarSesion(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.apply();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
