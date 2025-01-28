package com.example.recetasappproyectofinal.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (usuarioLogeado()) {
            cargarFragmento(new CategoriasFragment());
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
    }

    private boolean usuarioLogeado() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        return prefs.contains("correoUsuario");
    }

    private void cargarFragmento(Fragment fragmento) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, fragmento);
        transaction.commit();
    }
}
