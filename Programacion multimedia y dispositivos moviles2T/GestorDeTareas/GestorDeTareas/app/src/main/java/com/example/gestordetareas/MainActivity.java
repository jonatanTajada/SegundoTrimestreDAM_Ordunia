package com.example.gestordetareas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordetareas.vista.DashboardActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirigir al DashboardActivity directamente
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Cierra MainActivity para evitar que quede en segundo plano
    }
}
