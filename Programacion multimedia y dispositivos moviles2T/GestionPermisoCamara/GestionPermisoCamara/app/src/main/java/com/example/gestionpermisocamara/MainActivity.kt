package com.example.gestionpermisocamara

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPermisoCamara = findViewById<Button>(R.id.buttonPermisoCamara)

        // Acción al pulsar el botón
        buttonPermisoCamara.setOnClickListener {
            verificarPermisoCamara()
        }
    }

    private fun verificarPermisoCamara() {
        // Comprobar si el permiso ya fue concedido
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permiso ya concedido
                Toast.makeText(this, "Permiso de cámara ya concedido", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Solicitar permiso
                solicitarPermisoCamara()
            }
        }
    }

    private fun solicitarPermisoCamara() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permiso concedido
                    Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
                } else {
                    // Permiso denegado
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
            }

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}