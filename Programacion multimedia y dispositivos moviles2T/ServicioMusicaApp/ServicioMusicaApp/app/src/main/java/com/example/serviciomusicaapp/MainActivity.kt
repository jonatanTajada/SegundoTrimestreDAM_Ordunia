package com.example.serviciomusicaapp



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnReproducirMusica = findViewById<Button>(R.id.btnReproducirMusica)
        val btnDetenerMusica = findViewById<Button>(R.id.btnDetenerMusica)

        // Iniciar el servicio al pulsar "Reproducir Música"
        btnReproducirMusica.setOnClickListener {
            val intent = Intent(this, ServicioMusica::class.java)
            startService(intent)
        }

        // Detener el servicio al pulsar "Detener Música"
        btnDetenerMusica.setOnClickListener {
            val intent = Intent(this, ServicioMusica::class.java)
            stopService(intent)
        }
    }
}
