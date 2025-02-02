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

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        // ✔️ Si ya está logueado, lo mandamos a MainActivity
        if (sharedPreferences.getBoolean("logueado", false)) {
            irAlMain();
        }

        inicializarUI();
        btnLogin.setOnClickListener(v -> validarUsuario());
        btnRegistro.setOnClickListener(v -> irARegistro());
    }

    private void inicializarUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
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
            String passwordAlmacenada = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_ID));
            cursor.close();

            if (Encriptador.verificarPassword(password, passwordAlmacenada)) {
                guardarSesion(userId);
                irAlMain();
            } else {
                mostrarMensaje(getString(R.string.error_password));
            }
        } else {
            mostrarMensaje(getString(R.string.error_usuario_no_encontrado));
        }
    }

    private void guardarSesion(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.putBoolean("logueado", true);
        editor.apply();
    }

    private void irAlMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void irARegistro() {
        startActivity(new Intent(this, RegistroActivity.class));
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
