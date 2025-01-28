package com.example.gestordetareas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirigir automáticamente al Dashboard
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Cierra MainActivity para evitar que quede en segundo plano
    }
}
